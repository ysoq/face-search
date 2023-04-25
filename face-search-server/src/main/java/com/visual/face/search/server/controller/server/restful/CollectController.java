package com.visual.face.search.server.controller.server.restful;

import com.visual.face.search.server.controller.server.impl.CollectControllerImpl;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.CollectReqVo;
import com.visual.face.search.server.domain.response.CollectRepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags="01、集合(数据库)管理")
@RestController("visualCollectController")
@RequestMapping("/visual/collect")
public class CollectController extends CollectControllerImpl {

    @ApiOperation(value="1、创建一个集合(数据库)")
    @Override
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseInfo<Boolean> create(@RequestBody @Valid CollectReqVo collect) {
        return super.create(collect);
    }


    @ApiOperation(value="2、根据命名空间，集合名称删除集合")
    @Override
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseInfo<Boolean> delete(
            @ApiParam(value = "命名空间", name="namespace", required=true) @RequestParam(value = "namespace", required = true) String namespace,
            @ApiParam(value = "集合名称", name="collectionName", required=true) @RequestParam(value = "collectionName", required = true) String collectionName
    ) {
        return super.delete(namespace, collectionName);
    }

    @ApiOperation(value="3、根据命名空间，集合名称查看集合信息")
    @Override
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseInfo<CollectRepVo> get(
            @ApiParam(value = "命名空间", name="namespace", required=true) @RequestParam(value = "namespace", required = true) String namespace,
            @ApiParam(value = "集合名称", name="collectionName", required=true) @RequestParam(value = "collectionName", required = true) String collectionName
    ) {
        return super.get(namespace, collectionName);
    }

    @ApiOperation(value="4、根据命名空间查看集合列表")
    @Override
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseInfo<List<CollectRepVo>> list(
            @ApiParam(value = "命名空间", name="namespace", required=true) @RequestParam(value = "namespace", required = true) String namespace
    ) {
        return super.list(namespace);
    }
}
