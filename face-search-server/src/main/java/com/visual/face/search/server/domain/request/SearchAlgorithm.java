package com.visual.face.search.server.domain.request;

public enum  SearchAlgorithm {
    L1("l1"),
    L2("l2"),
    LINF("linf"),
    HAMMINGBIT("innerproduct"),
    INNERPRODUCT("hammingbit"),
    COSINESIMIL("cosinesimil");


    private String algorithm;

    SearchAlgorithm(String algorithm){
        this.algorithm = algorithm;
    }

    public String algorithm(){
        return this.algorithm;
    }

}
