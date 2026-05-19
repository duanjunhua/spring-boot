package com.duanjh.machine;

import weka.classifiers.trees.J48;
import weka.core.Instances;

import java.io.StringReader;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-30 周四 17:01
 * @Version: v1.0
 * @Description: 机器学习与深度学习
 */
public class MachineLearningDemo {

    public static void main(String[] args) throws Exception {
        machineLearning();
    }

    /**
     * 机器学习 vs 深度学习
     * 能自动从数据中发现规律，不需要人工编写复杂规则，单需要大量高质量数据，模型可能过拟合
     */
    private static void machineLearning() throws Exception {

        String data = carData();

        /**
         * 一个Instances代表一张表。能够相应一个arff文件或者是一个csv文件
         * 一个Instance代表一行记录
         */
        Instances instances = new Instances(new StringReader(data));
        instances.setClassIndex(instances.numAttributes() - 1);

        // 训练决策树模型
        J48 tree = new J48();
        // 传入一个Instances对象用于训练
        tree.buildClassifier(instances);

        // 模型可以预测：新的一天[rainy,68,80,TRUE] -> 会输出yes/no
        System.out.println("决策树模型：" + tree);
    }

    /**
     * 训练数据：天气情况 与 打网球 之间的关系
     *  Outlook, Temperature, Humidity, Windy, Play
     */
    private static String weatherData(){
        return "@relation weather\n" +
                "@attribute outlook {sunny,overcast,rainy}\n"+
                "@attribute temperature real\n"+
                "@attribute humidity real\n"+
                "@attribute windy {TRUE,FALSE}\n"+
                "@attribute play {yes,no}\n"+

                // 数据集
                "@data\n"+
                // 晴，实时温度85，实时湿度85，无风，不打球
                "sunny,85,85,FALSE,no\n"+
                // 晴，实时温度80，实时湿度90，有风，不打球
                "sunny,80,90,TRUE,no\n"+
                // 多云，实时温度83，实时湿度86，无风，打球
                "overcast,83,86,FALSE,yes\n"+
                // 阴雨，实时温度70，实时湿度96，无风，打球
                "rainy,70,96,FALSE,yes";
    }

    /**
     * 训练数据：第一次购买与最近购买时间 与 是否延保 的关系
     */
    private static String carData(){
        return "@relation bmwRes\n" +
                // 收入水平：0: <30k, 1: <40k, 2: <60k, 3: <75k, 4: <100k, 5: <150k, 6: <500k, 7: >=500k
                "@attribute income {0, 1, 2, 3, 4, 5, 6, 7}\n" +
                // 第一次买车的日期
                "@attribute firsBuy numeric\n" +
                // 最近一次买车日期
                "@attribute lastBuy numeric\n" +
                // 是否买延保
                "@attribute extendWarranty {1, 0}\n" +

                // 数据集
                "@data\n" +
                "4,200210,200601,0\n" +
                "5,200301,200601,1\n" +
                "6,200411,200601,0\n" +
                "5,199609,200603,0\n"
                ;
    }
}
