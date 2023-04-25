package com.visual.face.search.server.service.api;

import com.visual.face.search.server.model.TableColumn;

import java.util.List;
import java.util.Map;

public interface OperateTableService {

    boolean exist(String table);

    boolean dropTable(String table);

    boolean createImageTable(String table);

    boolean createSampleTable(String table, List<TableColumn> columns);

    boolean createFaceTable(String table, List<TableColumn> columns);
}
