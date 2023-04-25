package com.visual.face.search.server.controller.server.restful;

import com.visual.face.search.server.controller.server.impl.FaceDataControllerImpl;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.FaceDataReqVo;
import com.visual.face.search.server.domain.response.FaceDataRepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="03、人脸数据管理")
@RestController("visualFaceDataController")
@RequestMapping("/visual/face")
public class FaceDataController extends FaceDataControllerImpl {

    @ApiOperation(value="1、创建一个人脸数据")
    @Override
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseInfo<FaceDataRepVo> create(@RequestBody @Valid FaceDataReqVo face) {
        return super.create(face);
    }

    @ApiOperation(value="2、根据条件删除人脸数据")
    @Override
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseInfo<Boolean> delete(
            @ApiParam(value = "命名空间", name="namespace", required=true) @RequestParam(value = "namespace", required = true) String namespace,
            @ApiParam(value = "集合名称", name="collectionName", required=true) @RequestParam(value = "collectionName", required = true) String collectionName,
            @ApiParam(value = "样本ID", name="sampleId", required=true) @RequestParam(value = "sampleId", required = true) String sampleId,
            @ApiParam(value = "人脸ID", name="faceId", required=true) @RequestParam(value = "faceId", required = true) String faceId
    ) {
        return super.delete(namespace, collectionName, sampleId, faceId);
    }

}
