package cc.itsc.project.movie.review.backend.pojo.vo.rsp;

import cc.itsc.project.movie.review.backend.pojo.vo.HttpResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("反馈信息")
public class FeedbackRsp implements HttpResponse {
    @ApiModelProperty(value = "fid",example = "1234")
    private Long fid;

    @ApiModelProperty(value = "反馈用户uid",example = "123456")
    private Long uid;

    @ApiModelProperty(value = "反馈内容",example = "我是公告内容")
    private String content;

    @ApiModelProperty(value = "创建时间",example = "1235654645345")
    private Long createTime;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
