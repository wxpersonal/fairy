package me.weix.fairy.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.weix.fairy.pojo.Permission;
import me.weix.fairy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
@Api(value = "PermissionRest")
@Path("permission")
public class PermissionRest {

	@Autowired
	private IUserService userService;

	@ApiOperation(value = "根据id获取permission")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(value = "id/{id}")
	public Permission getPermissionById(@PathVariable Integer id) {
		return new Permission();
	}

}
