package cc.itsc.project.movie.review.backend.controller;

import cc.itsc.project.movie.review.backend.annotation.Security;
import cc.itsc.project.movie.review.backend.pojo.enums.RoleEnum;
import cc.itsc.project.movie.review.backend.pojo.vo.common.ServiceResponseMessage;
import cc.itsc.project.movie.review.backend.pojo.vo.req.ClassifiesReq;
import cc.itsc.project.movie.review.backend.pojo.vo.req.ModifyMovieReq;
import cc.itsc.project.movie.review.backend.pojo.vo.req.MovieDetailReq;
import cc.itsc.project.movie.review.backend.pojo.vo.req.MovieReviewReq;
import cc.itsc.project.movie.review.backend.pojo.vo.rsp.*;
import cc.itsc.project.movie.review.backend.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Leonardo iWzl
 * @version 1.0
 */

@Api(tags = "影视模块")
@RestController
@RequestMapping("/movie")
public class MovieController {
    private final
    MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @ApiOperation("# 新增电影分类")
    @Security(roles = RoleEnum.ADMIN)
    @PostMapping(value = "/classify",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<DefaultHttpRsp> insertNewMovieClassifyDetail(@RequestBody @Validated ClassifiesReq classifiesReq) {
        movieService.insertNewMovieClassifyDetail(classifiesReq.getClassifyList());
        return ServiceResponseMessage.createBySuccessCodeMessage();
    }
    @ApiOperation("#* 拉取电影分类")
    @Security(roles = RoleEnum.ALL,checkToken = false)
    @GetMapping(value = "/classify/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<ClassifiesRsp> searchAllMovieClassify() {
        List<String> classifyList = movieService.searchAllMovieClassify();
        ClassifiesRsp classifiesRsp = new ClassifiesRsp();
        classifiesRsp.setClassifyList(classifyList);
        return ServiceResponseMessage.createBySuccessCodeMessage(classifiesRsp);
    }

    @ApiOperation("#* 新增电影信息")
    @Security(roles = RoleEnum.ADMIN)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<DefaultHttpRsp> insertNewMovieDetail(@RequestBody @Validated MovieDetailReq movieDetailReq) {
        movieService.insertNewMovieDetail(movieDetailReq);
        return ServiceResponseMessage.createBySuccessCodeMessage();
    }

    @ApiOperation("#* 修改电影信息")
    @Security(roles = RoleEnum.ADMIN)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<DefaultHttpRsp> modifyMovieDetailByMid(@RequestBody @Validated ModifyMovieReq modifyMovieReq) {
        movieService.modifyMovieDetailByMid(modifyMovieReq);
        return ServiceResponseMessage.createBySuccessCodeMessage();
    }

    @ApiOperation("#* 删除电影信息")
    @Security(roles = RoleEnum.ADMIN)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<DefaultHttpRsp> deleteMovieByMid(@Min (value = 1) @Validated Long mid) {
        movieService.deleteMovieByMid(mid);
        return ServiceResponseMessage.createBySuccessCodeMessage();
    }

    @ApiOperation(value = "#* 按名称搜索电影基本信息",notes = "不包括电影影评")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNo",value = "页码数",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "页码大小",example = "10"),
            @ApiImplicitParam(name = "name",value = "查询Key",example = "天气之子")
    })
    @Security(roles = RoleEnum.ALL)
    @GetMapping(value = "/name",produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<PageOfInfoListRsp<MovieDetailRsp>> searchMovieDetailsByKeyWithPageInfo(
            @NotBlank(message = "搜索的Name不能为空") @Validated @RequestParam(value = "name") String name,
            @Min(value = 1,message = "页码数最少为1")@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
            @Min (value = 1,message = "每页数量最小为1")@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        PageOfInfoListRsp<MovieDetailRsp> pageOfMoviesList = movieService.searchMovieDetailsByKeyWithPageInfo(name,pageNo,pageSize);
        return ServiceResponseMessage.createBySuccessCodeMessage(pageOfMoviesList);
    }

    @ApiOperation(value = "#* 分类检索电影基本信息",notes = "不包括电影影评")
    @Security(roles = RoleEnum.ALL)
    @GetMapping(value = "/classify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<PageOfInfoListRsp<MovieDetailRsp>> searchMovieDetailsByClassify(
            @NotBlank(message = "搜索的classify不能为空") @Validated @RequestParam(value = "classify") String classify,
            @Min(value = 1,message = "页码数最少为1")@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
            @Min (value = 1,message = "每页数量最小为1")@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        PageOfInfoListRsp<MovieDetailRsp> pageOfMovieDetailList = movieService.searchMovieDetailsByClassifyWithPageInfo(classify,pageNo,pageSize);
        return ServiceResponseMessage.createBySuccessCodeMessage(pageOfMovieDetailList);
    }

    @ApiOperation(value = "#* 检索电影基本信息",notes = "不包括电影影评")
    @Security(roles = RoleEnum.ADMIN)
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<PageOfInfoListRsp<MovieDetailRsp>> searchAllMovieDetails(
            @Min(value = 1,message = "页码数最少为1")@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
            @Min (value = 1,message = "每页数量最小为1")@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        PageOfInfoListRsp<MovieDetailRsp> pageOfMovieDetailList = movieService.searchMovieDetailsByPageInfo(pageNo,pageSize);
        return ServiceResponseMessage.createBySuccessCodeMessage(pageOfMovieDetailList);
    }


    @ApiOperation(value = "#* 检索电影详细信息ByMid",notes = "包括电影影评")
    @Security(roles = RoleEnum.ALL)
    @GetMapping(value = "/mid",produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<MovieDetailRsp> fetchMovieDetailsByMid(@Min(value = 1,message = "电影MID不能为空") @RequestParam(value = "mid") Long mid) {
        MovieDetailRsp movieDetailRsp = movieService.fetchMovieDetailsByMid(mid);
        return ServiceResponseMessage.createBySuccessCodeMessage(movieDetailRsp);
    }


    @ApiOperation(value = "#* 发起影评",notes = "电影信息包括电影影评")
    @Security(roles = RoleEnum.USER)
    @PostMapping(value = "/review",produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<MovieDetailRsp> reviewMovieWithMid(@RequestBody @Validated MovieReviewReq movieReviewReq) {
        MovieDetailRsp movieDetailRsp = movieService.insertNewMovieReview(movieReviewReq);
        return ServiceResponseMessage.createBySuccessCodeMessage(movieDetailRsp);
    }


    @Security(roles = RoleEnum.ADMIN)
    @ApiOperation("# 分页拉取影评")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNo",value = "页码数",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "页码大小",example = "20")
    })
    @GetMapping(value = "/review/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<PageOfInfoListRsp<MovieReviewRsp>> fetchReviewPageOfMomentsReview(@Min(value = 1,message = "页码数最少为1")@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                                                          @Min (value = 1,message = "每页数量最小为1")@RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize) {
        PageOfInfoListRsp<MovieReviewRsp> pageOfMomentsReviewRep = movieService.fetchReviewPageOfMomentsReview(pageNo,pageSize);
        return ServiceResponseMessage.createBySuccessCodeMessage(pageOfMomentsReviewRep);
    }

    @ApiOperation(value = "#* 删除影评",notes = "删除影评")
    @Security(roles = RoleEnum.ADMIN)
    @DeleteMapping(value = "/review",produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponseMessage<DefaultHttpRsp> deleteReviewMovieWithRid(@Min (value = 1,message = "不能为空") @Validated Long rid) {
        movieService.deleteReviewMovieWithRid(rid);
        return ServiceResponseMessage.createBySuccessCodeMessage();
    }
}
