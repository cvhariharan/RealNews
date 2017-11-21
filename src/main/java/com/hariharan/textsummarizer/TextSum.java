/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hariharan.textsummarizer;
import fakenews.Collector;
import fakenews.Detector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.*;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * @author thero
 */
public class TextSum {
    
    public String text; //Holds the normalize text
    private String[] allWords;
    private HashMap<String, Integer> wordMap; //Stores the frequency of each word
    private HashMap<String, Double> sentences; //Holds all the sentences
    public HashMap<String, String> sentenceMap; //Holds normalized sentences with original sentences.
    public void setText(String text)
    {
        this.text = normalize(text);
        this.sentences = new HashMap<>();
        this.sentenceMap = new HashMap<>();
        for(String s: text.split("\\.")) //Escape for literal dot.
        {
            //System.out.println(s);
            this.sentences.put(normalize(s),1.0); //Initialize the score of each sentence as 1
            this.sentenceMap.put(normalize(s), s);
        }
        this.allWords = this.text.split(" ");
        this.wordMap = count(allWords);
        
    }
    
    public String normalize(String text)
    {
        String normalized = text.replaceAll("[^a-zA-Z]", " ").replaceAll(" +", " ").toLowerCase().trim();
        return normalized;
    }
    
    public void printText()
    {
        System.out.println(this.text);
    }
    
    public HashMap<String, Integer> count(String[] words)
    {
        HashMap<String, Integer> tempMap = new HashMap<>();
        for(String s: words)
        {
            if(tempMap.get(s) == null)
                tempMap.put(s, 1);
            else
                tempMap.put(s, tempMap.get(s) + 1);
        }
        return tempMap;
    }
    
    public void showFreq()
    {
        Set s = wordMap.keySet();
        Iterator i = s.iterator();
        while(i.hasNext())
        {
            String key = (String)i.next();
            System.out.println(key+":"+wordMap.get(key));
        }
    }
    
    public double computeCosine(String input)
    {
        //Finds the cosine between the input and all the other sentences in the document
        input = normalize(input);
        HashSet<String> inputVector = (HashSet)vectorize(input);
        HashSet<String> sentenceVector = null;
        Set<String> allSentences = this.sentences.keySet();
        double cosineSum = 0; //Sum of cosines of the input with all sentences.
        for(String eachSentence: allSentences)
        {
            if(!input.equals(eachSentence))
            {
            double cosine = 0;
            int count = 0;
            sentenceVector = (HashSet)vectorize(eachSentence);
            count = sentenceVector.size() + inputVector.size();
            sentenceVector.retainAll(inputVector);
            for(String t: sentenceVector)
            {
                int freq = wordMap.get(t);
                if(freq == 0)
                    freq = 1;
                cosine += Math.abs(Math.log10((double)count/(double)freq));
            }
            cosineSum += cosine;
            
        if(this.sentences.containsKey(input))
        {
            double score = this.sentences.get(input);
            this.sentences.put(input, score+cosineSum);
        }
        
        return cosineSum;
        }
        }
        
        return 0;
    }
    
    public Set<String> vectorize(String text)
    {
        Set<String> vector = new HashSet<>();
        for(String s: text.split(" "))
        {
            vector.add(s);
        }
        return vector;
    }
    
    public Map<String, Double> sortByValue(int count)
    {
        ValueComparator comp = new ValueComparator(this.sentences);
        Map<String, Double> sortedMap = new TreeMap(comp);
        sortedMap.putAll(this.sentences);
        Set<String> keys = sortedMap.keySet();
        System.out.println("SORTED...");
        int i = 0;
        for(String s: keys)
        {
            if(i!=count)
            {
                System.out.println(this.sentenceMap.get(s));
                i++;
            }
            else
                break;
        }
        return sortedMap;
    }
    public void showSentences()
    {
        Set<String> keys = this.sentences.keySet();
        Iterator i = keys.iterator();
        while(i.hasNext())
        {
            String sentence = (String)i.next();
            System.out.println(sentence+": "+this.sentences.get(sentence));
        }
    }
    public static void main(String[] args) throws IOException, Exception
    {
       /*IdfCounter counter = new IdfCounter();
       
       String news = "One thing is assured any time the pope \\u2013 any pope \\u2013 talks about a \\u201ccontroversial\\u201d topic. People will listen. Even people who have eschewed the Catholic Church and all she stands for if for no other reason to ridicule and lecture Catholics. Well, Pope Francis made a change, again, in the absolution procedures for the sin of procuring, assisting in or causing an abortion, and all hell has broken loose. Unlike earlier in the year when a procedural convenience was made permanent, this change actually is a change.\\n\\nAllow me to explain.\\n\\nIn the full list of sins for Catholics, abortion is one of a handful that are so heinous, they are in a category that automatically incurs what is called latae sententiae excommunication. That means that \\u201cby the act\\u201d the human puts himself or herself OUTSIDE of the Christian Community. This is not a formal bull of excommunication that is posted for heresy (teaching or espousing that which is against the Church in one way or another), but a very personal sort of separation. However, in order to have the excommunication lifted \\u2013 this is a spiritual remedy, not a punishment \\u2013 which would allow for absolution (it is not granted if one is excommunicated), there is some administrative paperwork that needs to happen.\\n\\nEarlier in his pontificate, Pope Francis officially transferred the paperwork procedure to the parishes for abortion, rather than sending it through the bishops\\u2019 offices, a practice that had been in place in the United States and other western nations infested with modernism for decades. It just made things smoother. With Monday\\u2019s move, Canon Law actually will have to be changed as the formal procedure of lifting the excommunication has been dropped.\\n\\n\\u201cI wish to restate as firmly as I can that abortion is a grave sin, since it puts an end to an innocent life,\\u201d the pope wrote. \\u201cIn the same way, however, I can and must state that there is no sin that God\\u2019s mercy cannot reach and wipe away when it finds a repentant heart seeking to be reconciled with the Father.\\u201d Speaking to reporters during a Vatican news conference Nov. 21, Archbishop Rino Fisichella said procuring an abortion still results in automatic excommunication the very moment the procedure is carried out. Sacramental absolution, therefore, is not just forgiving the sin of abortion, but also means \\u201cthe excommunication is removed,\\u201d he said. Now that all priests have been given the faculty to lift the excommunication and grant absolution, the Code of Canon Law will have to be updated, said the archbishop, who is president of the Pontifical Council for Promoting New Evangelization, the office that organized events for the Year of Mercy.\\n\\nPope Francis, truly, has picked up the baton of a number of late 19th century and 20th century figures in Catholicism when it comes to his message on God\\u2019s mercy. The main proponent of the message was Pope Saint John Paul II, along with a Polish nun known to him during his life who was canonized during his pontificate. Her name is St. Faustina, the Divine Mercy image to the left is taken from an entry in her papers, and her diary speaks of Divine Mercy in terms many never considered before. (It\\u2019s a tough, but good read.) By declaring the just completed Year of Mercy, Pope Francis reminds the flock that returning to God is always possible, yes, even for those who are involved with abortion. The Church is a hospital for sinners, not an exclusive club.\\n\\nIn Catholicism, though, there is a catch to receiving absolution no matter what your sins. Confession must be\\n\\nmade, and the Penance (the real name of the Sacrament) must be carried out with a contrite heart. We are supposed to be sorry for our sins. That\\u2019s something a lot of us need to work on.\\n\\nComments"
               + "";
       counter.setText(news);
       for(String s: news.split("\\."))
            System.out.println(s+": "+counter.computeCosine(s));
       counter.sortByValue();
       //counter.showSentences();*/
        TextSum c = new TextSum();
        Detector d = new Detector(c.normalize("Zimbabwe's President Robert Mugabe says he is resigning immediately and voluntarily in order to have a \"smooth transfer of power\" after 37 years in charge.\n" +
"The letter was read out in a cheering, dancing Parliament, which had been pursuing impeachment of the 93-year-old Mugabe, the world's oldest head of state.\n" +
"The resignation comes at the end of a week of extraordinary events that began with the military moving in last week, angered by Mugabe's firing of his longtime deputy and the positioning of the unpopular first lady to succeed him.\n" +
"Impeachment allegations against Mugabe included that he \"allowed his wife to usurp constitutional power\" and that he is \"of advanced age\" and too incapacitated to rule.\n" +
"Mugabe also was accused of allowing unpopular first lady Grace Mugabe to threaten to kill the recently fired Vice President Emmerson Mnangagwa and other officials.\n" +
"Earlier\n" +
"HARARE, Zimbabwe (AP) — Zimbabwe's Parliament launched impeachment proceedings against President Robert Mugabe on Tuesday, with both ruling party and opposition support, while the recently fired vice president said the world's oldest head of state should acknowledge the nation's \"insatiable desire\" for a leadership change and resign immediately.\n" +
"The statement by Emmerson Mnangagwa added to immense pressure on the 93-year-old Mugabe to quit after nearly four decades in power, during which he evolved from a champion of the fight against white minority rule into a figure blamed for a collapsing economy, government dysfunction and human rights violations.\n" +
"The ruling ZANU-PF party began impeachment proceedings against Mugabe after its Central Committee voted to oust the president as party leader and select Mnangagwa as his replacement, a move that eventually could allow the former vice president to become head of state. Mnangagwa served for decades as Mugabe's enforcer, with a reputation for being astute and ruthless, more feared than popular.\n" +
"Crowds rallied outside Parliament, dancing and singing. Some people placed photos of Mugabe in the street so that cars would run over them. Opposition leader Morgan Tsvangirai of the MDC party said the culture of the ruling party \"must end\" and everyone must put their heads together and work toward free and fair elections.\n" +
"The impeachment motion was introduced by the ruling party and seconded by the opposition MDC.\n" +
"\"The people of Zimbabwe have spoken with one voice and it is my appeal to President Mugabe that he should take heed of this clarion call and resign forthwith so that the country can move forward and preserve his legacy,\" Mnangagwa said in his statement, after more than a week of silence.\n" +
"Mnangagwa, who fled the country and has not appeared in public during the past week's political turmoil, said Mugabe had invited him to return to Zimbabwe \"for a discussion\" on recent events. However, he said he will not return for now, alleging that there had been plans to kill him at the time of his firing.\n" +
"\"I will be returning as soon as the right conditions for security and stability prevail,\" said Mnangagwa, who has a loyal support base in the military. \"Never should the nation be held at ransom by one person ever again, whose desire is to die in office at whatever cost to the nation.\"\n" +
"Zimbabwe's polarizing first lady, Grace Mugabe, had been positioning herself to succeed her husband, leading a party faction that engineered Mnangagwa's ouster. The prospect of a dynastic succession alarmed the military, which confined Mugabe to his home last week and targeted what it called \"criminals\" around him who allegedly were looting state resources — a reference to associates of the first lady.\n" +
"Mnangagwa was targeted by U.S. sanctions in the early 2000s for undermining democratic development in Zimbabwe, according to the Atlantic Council, a U.S.-based policy institute. However, J. Peter Pham, an Africa expert at the council, noted that some Zimbabwean opposition figures have appeared willing to have dialogue with Mnangagwa in order to move the country forward and that the international community should consider doing the same.\n" +
"\"We're not saying whitewash the past, but it is in the interests of everyone that Zimbabwe is engaged at this critical time,\" Pham said in a statement.\n" +
"Regional leaders continued efforts to find a solution to the political turmoil, with South Africa's state-run broadcaster reporting that the presidents of South Africa and Angola would travel to Zimbabwe on Wednesday to meet with \"stakeholders\" in the political crisis, including Mugabe and the military.\n" +
"Impeachment proceedings began days after huge crowds surged through the capital, Harare, to demand that Mugabe quit. The ruling party had instructed government ministers to boycott a Cabinet meeting that Mugabe called for Tuesday morning at State House, the president's official residence, and instead attend a meeting at party headquarters to work on the impeachment.\n" +
"The ruling party says the charges for impeachment include that Mugabe \"allowed his wife to usurp constitutional power\" and that he is \"of advanced age\" and no longer has the physical capacity to run the government.\n" +
"It was not clear how long the impeachment process could take. The ruling party has said Mugabe could be voted out as early as Wednesday but some analysts believe the impeachment process could take weeks and would, if conducted properly, allow Mugabe to make a case in his defense.\n" +
"Mnangagwa said he was aware of the move to impeach Mugabe. Though unpopular in some parts of Zimbabwe, the former vice president called for unity and appeared to embrace the prospect of taking over power.\n" +
"\"I will not stand in the way of the people and my party,\" he said."));
        System.out.println(d.detect());
        String news = "Zimbabwe's President Robert Mugabe says he is resigning immediately and voluntarily in order to have a \"smooth transfer of power\" after 37 years in charge.\n" +
"The letter was read out in a cheering, dancing Parliament, which had been pursuing impeachment of the 93-year-old Mugabe, the world's oldest head of state.\n" +
"The resignation comes at the end of a week of extraordinary events that began with the military moving in last week, angered by Mugabe's firing of his longtime deputy and the positioning of the unpopular first lady to succeed him.\n" +
"Impeachment allegations against Mugabe included that he \"allowed his wife to usurp constitutional power\" and that he is \"of advanced age\" and too incapacitated to rule.\n" +
"Mugabe also was accused of allowing unpopular first lady Grace Mugabe to threaten to kill the recently fired Vice President Emmerson Mnangagwa and other officials.\n" +
"Earlier\n" +
"HARARE, Zimbabwe (AP) — Zimbabwe's Parliament launched impeachment proceedings against President Robert Mugabe on Tuesday, with both ruling party and opposition support, while the recently fired vice president said the world's oldest head of state should acknowledge the nation's \"insatiable desire\" for a leadership change and resign immediately.\n" +
"The statement by Emmerson Mnangagwa added to immense pressure on the 93-year-old Mugabe to quit after nearly four decades in power, during which he evolved from a champion of the fight against white minority rule into a figure blamed for a collapsing economy, government dysfunction and human rights violations.\n" +
"The ruling ZANU-PF party began impeachment proceedings against Mugabe after its Central Committee voted to oust the president as party leader and select Mnangagwa as his replacement, a move that eventually could allow the former vice president to become head of state. Mnangagwa served for decades as Mugabe's enforcer, with a reputation for being astute and ruthless, more feared than popular.\n" +
"Crowds rallied outside Parliament, dancing and singing. Some people placed photos of Mugabe in the street so that cars would run over them. Opposition leader Morgan Tsvangirai of the MDC party said the culture of the ruling party \"must end\" and everyone must put their heads together and work toward free and fair elections.\n" +
"The impeachment motion was introduced by the ruling party and seconded by the opposition MDC.\n" +
"\"The people of Zimbabwe have spoken with one voice and it is my appeal to President Mugabe that he should take heed of this clarion call and resign forthwith so that the country can move forward and preserve his legacy,\" Mnangagwa said in his statement, after more than a week of silence.\n" +
"Mnangagwa, who fled the country and has not appeared in public during the past week's political turmoil, said Mugabe had invited him to return to Zimbabwe \"for a discussion\" on recent events. However, he said he will not return for now, alleging that there had been plans to kill him at the time of his firing.\n" +
"\"I will be returning as soon as the right conditions for security and stability prevail,\" said Mnangagwa, who has a loyal support base in the military. \"Never should the nation be held at ransom by one person ever again, whose desire is to die in office at whatever cost to the nation.\"\n" +
"Zimbabwe's polarizing first lady, Grace Mugabe, had been positioning herself to succeed her husband, leading a party faction that engineered Mnangagwa's ouster. The prospect of a dynastic succession alarmed the military, which confined Mugabe to his home last week and targeted what it called \"criminals\" around him who allegedly were looting state resources — a reference to associates of the first lady.\n" +
"Mnangagwa was targeted by U.S. sanctions in the early 2000s for undermining democratic development in Zimbabwe, according to the Atlantic Council, a U.S.-based policy institute. However, J. Peter Pham, an Africa expert at the council, noted that some Zimbabwean opposition figures have appeared willing to have dialogue with Mnangagwa in order to move the country forward and that the international community should consider doing the same.\n" +
"\"We're not saying whitewash the past, but it is in the interests of everyone that Zimbabwe is engaged at this critical time,\" Pham said in a statement.\n" +
"Regional leaders continued efforts to find a solution to the political turmoil, with South Africa's state-run broadcaster reporting that the presidents of South Africa and Angola would travel to Zimbabwe on Wednesday to meet with \"stakeholders\" in the political crisis, including Mugabe and the military.\n" +
"Impeachment proceedings began days after huge crowds surged through the capital, Harare, to demand that Mugabe quit. The ruling party had instructed government ministers to boycott a Cabinet meeting that Mugabe called for Tuesday morning at State House, the president's official residence, and instead attend a meeting at party headquarters to work on the impeachment.\n" +
"The ruling party says the charges for impeachment include that Mugabe \"allowed his wife to usurp constitutional power\" and that he is \"of advanced age\" and no longer has the physical capacity to run the government.\n" +
"It was not clear how long the impeachment process could take. The ruling party has said Mugabe could be voted out as early as Wednesday but some analysts believe the impeachment process could take weeks and would, if conducted properly, allow Mugabe to make a case in his defense.\n" +
"Mnangagwa said he was aware of the move to impeach Mugabe. Though unpopular in some parts of Zimbabwe, the former vice president called for unity and appeared to embrace the prospect of taking over power.\n" +
"\"I will not stand in the way of the people and my party,\" he said.";
        
        c.setText(news);
        for(String s: news.split("\\."))
        {
            c.computeCosine(s);
        }
        c.sortByValue(5);
    }
}
