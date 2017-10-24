package me.weix.fairy.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.weix.fairy.config.dataSource.DataSourceContextHolder;
import me.weix.fairy.config.dataSource.DataSourceType;
import me.weix.fairy.pojo.User;
import me.weix.fairy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
@Api(value = "user")
@Path("user")
public class UserRest {

    @Autowired
    private IUserService userService;


    @ApiOperation(value = "根据id获取user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(value = "{id}")
    public User getUserById(@PathParam("id") Integer id) {
        DataSourceContextHolder.setDataSource(DataSourceType.master.getName());
        User byId = userService.getById(id);
        return byId;
    }

    @ApiOperation(value = "添加user")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Integer addUser(User u) {
        System.out.printf(u.getEmail());
        return 111;//userService.insert(u);
    }

    @ApiOperation(value = "更新user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Integer updateUser(User u) {
        System.out.printf(u.getEmail());
        return 111;//userService.insert(u);
    }


}
