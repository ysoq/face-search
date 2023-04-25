package com.visual.face.search.server.controller.server.restful;

import com.visual.face.search.server.controller.server.impl.FaceCompareControllerImpl;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.FaceCompareReqVo;
import com.visual.face.search.server.domain.response.FaceCompareRepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="05、人脸比对服务")
@RestController("visualFaceCompareController")
@RequestMapping("/visual/compare")
public class FaceCompareController extends FaceCompareControllerImpl {


    @ApiOperation(value="1、人脸比对1:1", position = 1)
    @Override
    @ResponseBody
    @RequestMapping(value = "/do", method = RequestMethod.POST)
    public ResponseInfo<FaceCompareRepVo> faceCompare(@RequestBody @Valid FaceCompareReqVo compareReq) {
        return super.faceCompare(compareReq);
    }
}
