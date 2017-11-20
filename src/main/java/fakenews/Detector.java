/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fakenews;
import weka.core.Instances;
import java.io.*;
import weka.classifiers.functions.SGDText;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.converters.ConverterUtils.DataSource;
/**
 *
 * @author thero
 */
public class Detector {
  
    public Detector() throws Exception
    {
        DataSource source = new DataSource("TestDataset.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);
        SGDText sgd = new SGDText();
        sgd = (SGDText)weka.core.SerializationHelper.read("fake_news_detector_model2.model");
        
        for(int i = 0; i < data.numInstances(); i++)
        {
            Instance newInst = data.instance(i);
            System.out.println(sgd.classifyInstance(newInst));
        }
    }
}
