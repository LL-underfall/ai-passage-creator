package com.yupi.template.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.yupi.template.model.dto.article.ArticleQueryRequest;
import com.yupi.template.model.dto.article.ArticleState;
import com.yupi.template.model.entity.Article;
import com.yupi.template.model.entity.User;
import com.yupi.template.model.enums.ArticleStatusEnum;
import com.yupi.template.model.vo.ArticleVO;

public interface ArticleService extends IService<Article> {
    String createArticleTask(String topic, User loginUser);

    Article getByTaskId(String taskId);

    void updateArticleStatus(String taskId, ArticleStatusEnum status, String errorMessage);

    void saveArticleContent(String taskId, ArticleState state);

    ArticleVO getArticleDetail(String taskId, User loginUser);

    Page<ArticleVO> listArticleByPage(ArticleQueryRequest request, User loginUser);

    boolean deleteArticle(Long id, User loginUser);
}
