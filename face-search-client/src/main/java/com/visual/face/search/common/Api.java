package com.visual.face.search.common;

public class Api {

    public static final String collect_get = "/visual/collect/get";
    public static final String collect_list = "/visual/collect/list";
    public static final String collect_create = "/visual/collect/create";
    public static final String collect_delete = "/visual/collect/delete";

    public static final String sample_get = "/visual/sample/get";
    public static final String sample_list = "/visual/sample/list";
    public static final String sample_create = "/visual/sample/create";
    public static final String sample_update = "/visual/sample/update";
    public static final String sample_delete = "/visual/sample/delete";

    public static final String face_delete = "/visual/face/delete";
    public static final String face_create = "/visual/face/create";

    public static final String visual_search = "/visual/search/do";
    public static final String visual_compare = "/visual/compare/do";

    public static String getUrl(String host, String uri){
        host = host.replaceAll ("/+$", "");
        return host + uri;
    }

}
