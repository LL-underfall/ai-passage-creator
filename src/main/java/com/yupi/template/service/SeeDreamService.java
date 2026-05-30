package com.yupi.template.service;

import com.volcengine.ark.runtime.model.images.generation.GenerateImagesRequest;
import com.volcengine.ark.runtime.model.images.generation.ImagesResponse;
import com.volcengine.ark.runtime.service.ArkService;
import com.yupi.template.config.SeeDreamConfig;
import com.yupi.template.model.dto.image.ImageData;
import com.yupi.template.model.dto.image.ImageRequest;
import com.yupi.template.model.enums.ImageMethodEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.yupi.template.constant.ArticleConstant.PICSUM_URL_TEMPLATE;

/**
 * SeeDream (doubao 原生图片生成) 服务
 * 使用 SeeDream 4.0 模型生成图片
 *
 * @author <a href="https://codefather.cn">编程导航学习圈</a>
 */
@Service
@Slf4j
public class SeeDreamService implements ImageSearchService {

    @Resource
    private SeeDreamConfig.ArkServiceFactory arkServiceFactory;

    @Override
    public String searchImage(String keywords) {
        // 此方法已废弃，请使用 getImageData()
        // 返回 null，上传逻辑由 ImageServiceStrategy 统一处理
        return null;
    }

    @Override
    public ImageData getImageData(ImageRequest request) {
        String prompt = request.getEffectiveParam(true);
        return generateImageData(prompt);
    }

    /**
     * 根据提示词生成图片数据
     *
     * @param prompt 生图提示词
     * @return ImageData 包含图片字节数据，生成失败返回 null
     */
    public ImageData generateImageData(String prompt) {
        try {
            GenerateImagesRequest request = arkServiceFactory.generateRequest(prompt, "1K");
            ArkService arkService = arkServiceFactory.createArkService();
            try {
                log.info("SeeDream 开始生成图片, model={}, prompt={}", arkServiceFactory.getModel(), prompt);

                ImagesResponse imagesResponse = arkService.generateImages(request);

                // 提取图片数据
                byte[] bytes = imagesResponse.getData().get(0).getB64Json().getBytes();

                String mimeType = "image/jpeg"; // SeeDream 4.0 默认 jpeg
                log.info("SeeDream 图片生成成功, size={} bytes, mimeType={}", bytes.length, mimeType);
                return ImageData.fromBytes(bytes, mimeType);
            } finally {
                arkService.shutdownExecutor();
            }
        } catch (Exception e) {
            log.error("SeeDream 生成图片异常, prompt={}", prompt, e);
            return null;
        }
    }

    @Override
    public ImageMethodEnum getMethod() {
        return ImageMethodEnum.SEE_DREAM;
    }

    @Override
    public String getFallbackImage(int position) {
        return String.format(PICSUM_URL_TEMPLATE, position);
    }
}
