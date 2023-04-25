package com.visual.face.search.server.controller.server.restful;

import com.visual.face.search.server.controller.server.impl.SampleDataControllerImpl;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.SampleDataReqVo;
import com.visual.face.search.server.domain.response.SampleDataRepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags="02、人脸样本管理")
@RestController("visualSampleDataController")
@RequestMapping("/visual/sample")
public class SampleDataController extends SampleDataControllerImpl {

    @ApiOperation(value="1、创建一个样本", position = 1)
    @Override
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseInfo<Boolean> create(@RequestBody @Valid SampleDataReqVo sample) {
        return super.create(sample);
    }

    @ApiOperation(value="2、更新一个样本", position = 2)
    @Override
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseInfo<Boolean> update(@RequestBody @Valid SampleDataReqVo sample) {
        return super.update(sample);
    }

    @ApiOperation(value="3、根据条件删除样本", position = 3)
    @Override
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseInfo<Boolean> delete(
            @ApiParam(value = "命名空间", name="namespace", required=true) @RequestParam(value = "namespace", required = true) String namespace,
            @ApiParam(value = "集合名称", name="collectionName", required=true) @RequestParam(value = "collectionName", required = true) String collectionName,
            @ApiParam(value = "样本ID", name="sampleId", required=true) @RequestParam(value = "sampleId", required = true) String sampleId
    ) {
        return super.delete(namespace, collectionName, sampleId);
    }

    @ApiOperation(value="4、根据条件查看样本", position = 4)
    @Override
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseInfo<SampleDataRepVo> get(
            @ApiParam(value = "命名空间", name="namespace", required=true) @RequestParam(value = "namespace", required = true) String namespace,
            @ApiParam(value = "集合名称", name="collectionName", required=true) @RequestParam(value = "collectionName", required = true) String collectionName,
            @ApiParam(value = "样本ID", name="sampleId", required=true) @RequestParam(value = "sampleId", required = true) String sampleId
    ) {
        return super.get(namespace, collectionName, sampleId);
    }

    @ApiOperation(value="5、根据查询信息查看样本列表", position = 5)
    @Override
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseInfo<List<SampleDataRepVo>> list(
            @ApiParam(value = "命名空间", name="namespace", required=true) @RequestParam(value = "namespace", required = true) String namespace,
            @ApiParam(value = "集合名称", name="collectionName", required=true) @RequestParam(value = "collectionName", required = true) String collectionName,
            @ApiParam(value = "起始记录:默认0", name="offset", required=true, example = "0") @RequestParam(value = "offset", defaultValue = "0", required = false) Integer offset,
            @ApiParam(value = "样本数目：默认10", name="limit", required=true, example = "10") @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
            @ApiParam(value = "排列方式：默认asc，包括asc（升序）和desc（降序）", name="order", required=true) @RequestParam(value = "order", required = false) String order
    ) {
        return super.list(namespace, collectionName, offset, limit, order);
    }

}
