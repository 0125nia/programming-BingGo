package com.bingo.data.bpFileTree;



import com.bingo.data.fileUtil.FileProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Table {
    FileProcessor filp=new FileProcessor();
    String path;
    BpFileTree bp[];                //多个b+树索引
    public Table(String path){
//        扫描path，找到所有索引，放入bp[]
        if(!filp.exist(path)){
            filp.createFolder(path);
        }
        LinkedList<String> children = filp.getChildren(path);           //拿到所有path下文件夹
        bp=new BpFileTree[20];
        for(String child:children){
            int idx=Integer.parseInt(child);
            bp[idx]=new BpFileTree(5,path+"\\"+idx);
        }
        this.path=path;
    }

    //    创建索引
    public void createIndex(int colIdx){
        bp[colIdx]=new BpFileTree(5,path+"\\"+colIdx);
    }
    //    整体导入：覆盖
    public void bulkImport(String total,int length) throws IOException {
        for(int i=0;i<20;i++){
            if(bp[i]!=null){
                filp.folderRemove(bp[i].root);                  //删除原有的库
                bp[i]=new BpFileTree(5,bp[i].root);
                _bulkImport(total,length,i);                    //导入新的库
            }
        }
        generateCSV();                                          //生成csv
    }
    //    bulkImport的子函数，用于将一个txt文件中的数据整体导入
    private LinkedList<String> _bulkImport(String total,int length,int colIdx){
        LinkedList<String> datas=new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(total))) {
            String line;
            String mainCode=null;
            int index = 0;
            int i=0;

            while ((line=reader.readLine() ) != null) {
                if(index==length){                          //数量够后，添加，datas置空、index置0
                    System.out.println(mainCode+":"+i++);
//                    bp.insert(mainCode);
                    bp[colIdx].insert(mainCode,datas.toArray(new String[0]));
//                    bp[colIdx].printTree(bp[colIdx].root);
//                    System.out.println();
                    datas.clear();
                    index=0;
                }
                if(index==colIdx)                                //拿到主键id
                    mainCode=line;
                datas.add(line);
                index++;
            }
            //            处理txt中最后一个数据
//            System.out.println(mainCode+":"+i++);
            bp[colIdx].insert(mainCode,datas.toArray(new String[0]));
//            bp[colIdx].printTree(bp[colIdx].root);
//            System.out.println();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return datas;
    }
    //    单个数据导入
    public void insert(LinkedList<String> list) {
        try {
//        在每个索引中都导入这个数据
            for(int i=0;i<20;i++){
                if(bp[i]!=null){
                    bp[i].insert(list.get(i),list.toArray(new String[0]));
                }
            }
            generateCSV();                                          //生成csv
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //    单个数据删除
    public boolean removeByIdx(LinkedList<String> list,int idx) throws IOException {
//        首先比对数据是否完全相等
        if(!findByIdx(idx,list.get(idx)).equals(list))return false;
//        在每个索引中都删除这个数据
        for(int i=0;i<20;i++){
            if(bp[i]!=null){
                if(!bp[i].remove(list.get(i)))return false;
            }
        }
        generateCSV();                                          //生成csv
        return true;
    }
    //    通过对应索引树查找数据:
    public LinkedList<String> findByIdx(int colIdx,String value){
        LinkedList<String> res=new LinkedList<>();
        String getPath = bp[colIdx].find(value);
        if(getPath!=null){
            res = filp.getContext(getPath+"\\data", value + ".txt");
        }
        return res;
    }
    //    单个数据修改
    public boolean updateByFristIdx(LinkedList<String> list){
        try {
            boolean ifsuccess=false;
//        在第一个索引中更新这个数据
            for(int i=0;i<20;i++){
                if(bp[i]!=null){
                    ifsuccess=bp[i].update(list.get(i),list.toArray(new String[0]));
                    if(!ifsuccess)return ifsuccess;
                }
                break;
            }
            generateCSV();                  //生成csv
            importByCSV();                  //通过csv修改其他索引，达到修改所有索引的目的
            return ifsuccess;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //    生成or更新csv文件:返回csv文件的地址
    private String generateCSV() throws IOException {
        int colIdx = 0;
        for(int i=0;i<20;i++){
            if(bp[i]!=null){
                colIdx=i;
                break;
            }
        }
        if(bp[colIdx]==null)return null;
        String p_csv=path+"\\data.csv";
        LinkedList<String> allLeaves = bp[colIdx].getAllLeaves(bp[colIdx].root);
//        for(String leaf:allLeaves){
//            System.out.println(leaf);
//        }
        //        用于存储所有txt数据
        LinkedList<LinkedList<String>> allTxtContexts = new LinkedList<>();
        for(String leaf:allLeaves){
            //            首先拿到所有的key
            LinkedList<String> keys = filp.getContext(leaf, "keys.txt");
            //            拿到所有txt中的数据
            for(String key:keys){
                LinkedList<String> txtContext = filp.getContext(leaf + "\\data", key + ".txt");
                allTxtContexts.add(txtContext);
            }
        }
        //        将数据放入scv
        filp.writeDataToCSVFile(allTxtContexts,p_csv);

//        //        测试========================
//        for(LinkedList<String> txtContxt:allTxtContexts){
//            for (String s:txtContxt){
//                System.out.println(s);
//            }
//        }
        //        koko made=====================
        return path+"\\data.csv";
    }
    //    生成or更新csv文件:返回csv文件的地址(通过指定列更新)
    private String generateCSV(int colIdx) throws IOException {
        String p_csv=path+"\\data.csv";
        LinkedList<String> allLeaves = bp[colIdx].getAllLeaves(bp[colIdx].root);
        for(String leaf:allLeaves){
            System.out.println(leaf);
        }
        //        用于存储所有txt数据
        LinkedList<LinkedList<String>> allTxtContexts = new LinkedList<>();
        for(String leaf:allLeaves){
            //            首先拿到所有的key
            LinkedList<String> keys = filp.getContext(leaf, "keys.txt");
            //            拿到所有txt中的数据
            for(String key:keys){
                LinkedList<String> txtContext = filp.getContext(leaf + "\\data", key + ".txt");
                allTxtContexts.add(txtContext);
            }
        }
        //        将数据放入scv
        filp.writeDataToCSVFile(allTxtContexts,p_csv);

        //        测试========================
        for(LinkedList<String> txtContxt:allTxtContexts){
            for (String s:txtContxt){
                System.out.println(s);
            }
        }
        //        koko made=====================
        return path+"\\data.csv";
    }
    //    根据csv文件更新数据库
    public boolean importByCSV() throws IOException {
        if(!filp.exist(path+"\\data.csv"))return false;
        LinkedList<LinkedList<String>> linkedLists = filp.readCsv(path + "\\data.csv");
//        存在，那么：
//          先删除原有的库，再导入
        for(int i=0;i<20;i++){
            if(bp[i]!=null){
                filp.folderRemove(bp[i].root);                  //删除原有的库
                bp[i]=new BpFileTree(5,bp[i].root);
                for(LinkedList<String> linkedList:linkedLists){ //导入新的库
                    bp[i].insert(linkedList.get(i),linkedList.toArray(new String[0]));
                }
            }
        }
//        for(LinkedList<String> linkedList:linkedLists){
//            for(String s:linkedList){
//                System.out.print(s+" ");
//            }
//            System.out.println();
//        }
        return true;
    }

    //    根据csv文件拿到所有数据
    public LinkedList<LinkedList<String>> getAllByCSV(){
        if(!filp.exist(path+"\\data.csv"))return null;
        LinkedList<LinkedList<String>> linkedLists = filp.readCsv(path + "\\data.csv");
//        存在，那么：
        return linkedLists;
    }
    //    根据csv文件在控制台显示所有数据
    public boolean showAllByCSV() throws IOException {
        if(!filp.exist(path+"\\data.csv"))return false;
        LinkedList<LinkedList<String>> linkedLists = filp.readCsv(path + "\\data.csv");
        //        存在，那么：
        for(LinkedList<String> linkedList:linkedLists){
            for(String s:linkedList){
                System.out.print(s+"\t");
            }
            System.out.println();
        }
        return true;
    }
}