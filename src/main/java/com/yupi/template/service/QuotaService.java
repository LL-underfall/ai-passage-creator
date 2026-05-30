package com.yupi.template.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.yupi.template.model.dto.article.ArticleQueryRequest;
import com.yupi.template.model.dto.article.ArticleState;
import com.yupi.template.model.entity.Article;
import com.yupi.template.model.entity.User;
import com.yupi.template.model.enums.ArticleStatusEnum;
import com.yupi.template.model.vo.ArticleVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuotaService {

    boolean hasQuota(User user);

    @Transactional(rollbackFor = Exception.class)
    void consumeQuota(User user);

    @Transactional(rollbackFor = Exception.class)
    void checkAndConsumeQuota(User user);
}
