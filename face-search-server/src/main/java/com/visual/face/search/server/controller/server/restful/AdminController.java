package com.visual.face.search.server.controller.server.restful;

import com.visual.face.search.server.controller.server.impl.AdminControllerImpl;
import com.visual.face.search.server.domain.common.ResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags="06、管理员接口")
@RestController("visualAdminController")
@RequestMapping("/visual/admin")
public class AdminController extends AdminControllerImpl {

    @ApiOperation(value="1、获取命名空间集合列表")
    @Override
    @ResponseBody
    @RequestMapping(value = "/getNamespaceList", method = RequestMethod.GET)
    public ResponseInfo<List<String>> getNamespaceList() {
        return super.getNamespaceList();
    }

    @ApiOperation(value="2、根据命名空间查看集合列表")
    @Override
    @ResponseBody
    @RequestMapping(value = "/getCollectList", method = RequestMethod.GET)
    public ResponseInfo<List<Map<String, String>>> getCollectList(@ApiParam(value = "命名空间", name="namespace", required=true) @RequestParam(value = "namespace", required = true) String namespace) {
        return super.getCollectList(namespace);
    }

}
