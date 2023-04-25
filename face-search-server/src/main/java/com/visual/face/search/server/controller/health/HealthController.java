package com.visual.face.search.server.controller.health;

import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.utils.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@Api(tags="06、公共服务-健康检测")
@RestController("healthController")
@RequestMapping("/common/health")
public class HealthController {

    @ApiOperation(value="公共-服务健康检测")
    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseInfo<String> check(){
        return ResponseBuilder.success("health check is ok");
    }

}
