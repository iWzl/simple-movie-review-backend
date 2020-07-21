package cc.itsc.project.movie.review.backend.service;

import cc.itsc.project.movie.review.backend.pojo.vo.common.ServiceResponseMessage;
import cc.itsc.project.movie.review.backend.pojo.vo.rsp.UploadImageRsp;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Leonardo iWzl
 * @version 1.0
 * @date 2020/2/22 11:06
 */
public interface CommonService {
    /**
     * 上传保存图片文件信息
     *
     * @param imageFile 图片文件地址
     * @return 文件上传成功的图片地址
     */
    String uploadMultipartFile(MultipartFile imageFile);
}
