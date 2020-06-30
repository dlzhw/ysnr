package com.zhw.mes.support.scales;


import com.zhw.mes.support.commport.MesSerialPort;
import com.zhw.mes.support.commport.MesSerialPortManager;
import com.zhw.mes.support.scales.protocol.MaterialWeight;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.apache.commons.math3.stat.StatUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 电子称
 */
public abstract class ElectricalScale{
    /**
     * 连接的串口名称
     */
    private String serialPortName;

    /**
     * 电子称连接的串口
     */
    private MesSerialPort serialPort;

    /**
     * 串口数据传输波特率
     */
    private int baudRate;
    /**
     * 数据位,串口业务数据包的大小
     */
    private int dataBits;
    /**
     * 停止位
     */
    private int stopBit;
    /**
     * 检验位
     */
    private int parityBit;

    /**
     * 电子称名称
     */
    private String name;

    /**
     *监听串口数据
     */
    private Flowable<MaterialWeight> myObserver ;
    /**
     * 用于释放订阅
     */
    private Disposable subscribe;

    /**
     * 物料重量的消费者
     */
    private WeightConsumer weightConsumer;

    /**
     *
     * @param serialPortName 连接串口名称
     * @param baudRate
     * @param dataBits
     * @param stopBit
     * @param parityBit
     * @param name 电子称名称
     */
    public ElectricalScale(String serialPortName, int baudRate, int dataBits, int stopBit, int parityBit, WeightConsumer weightConsumer, String name) {
        this.serialPortName = serialPortName;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBit = stopBit;
        this.parityBit = parityBit;
        this.name = name;
        this.weightConsumer = weightConsumer;

    }

    public Disposable subscribe(Consumer<? super MaterialWeight> onNext) {
        return myObserver.subscribe(onNext);
    }


    /**
     * 启动电子称
     */
    public void start(){
        myObserver = Flowable.create((emitter)->{
            serialPort = MesSerialPortManager.findSerialPort(serialPortName);
            serialPort.open(2000);
            serialPort.setSerialPortParams(baudRate,dataBits,stopBit,parityBit);
            serialPort.setListener((buff)->{
                MaterialWeight weight = parseWeight(buff);
                emitter.onNext(weight);
            });

        }, BackpressureStrategy.BUFFER);

        subscribe = myObserver.window(1, TimeUnit.SECONDS)
                .subscribe((flowable)->{
                    flowable.toList().subscribe((list) -> {
                        caculateWeight(list);
                    });
                });
    }

    /**
     * 根据单组串口数据,计算物料重量
     * @param list 每称串口传送过来的重量数据,这组数据中可能包含串口传输过程中的错误数据
     *             也可能包含电子称还未稳定时的不准确数据.
     */
    private void caculateWeight(List<MaterialWeight> list) {
        int len = list.size();
        if(len < 5){
            //忽略
            return;
        }
        Collections.sort(list, new Comparator<MaterialWeight>() {
            @Override
            public int compare(MaterialWeight o1, MaterialWeight o2) {
                return Double.compare(o1.getWeight() , o2.getWeight());
            }
        });

        //取中间五分之二的数据作为准确数据,基于电子称传过来的绝大多数据是准确的假设.
        int mid = list.size()/2;
        int span = list.size()/5;
        List<MaterialWeight> samples = list.subList(mid - span, mid + span);
        double [] weights = new double[samples.size()];
        for(int i=0;i<samples.size();i++){
            weights[i] = samples.get(i).getWeight();
        }
        //计算
        double mean = StatUtils.mean(weights);
        //todo 还要计算标准差,确定误差范围
        if(this.weightConsumer != null){
            MaterialWeight materialWeight = new MaterialWeight();
            materialWeight.setWeight(mean);
            weightConsumer.accept(materialWeight);
        }
        //System.out.println("StatUtils.mode(weights) = " + Arrays.toString(StatUtils.mode(weights)));
        //System.out.println("StatUtils.mean(weights) = " + StatUtils.mean(weights));
        //System.out.println("list = " + list);
    }

    /**
     * 关闭电子称
     */
    public void stop(){
        subscribe.dispose();
        serialPort.close();
    }

    /**
     * 解析数据包
     * @param dataPackage
     * @return
     */
    protected abstract MaterialWeight parseWeight(byte[] dataPackage);

    public String getSerialPortName() {
        return serialPortName;
    }

    public void setSerialPortName(String serialPortName) {
        this.serialPortName = serialPortName;
    }

    public MesSerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(MesSerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBit() {
        return stopBit;
    }

    public void setStopBit(int stopBit) {
        this.stopBit = stopBit;
    }

    public int getParityBit() {
        return parityBit;
    }

    public void setParityBit(int parityBit) {
        this.parityBit = parityBit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeightConsumer(WeightConsumer weightConsumer) {
        this.weightConsumer = weightConsumer;
    }
}
