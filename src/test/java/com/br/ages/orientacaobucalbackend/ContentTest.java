// TODO: Change to cucumber format

// package com.br.ages.orientacaobucalbackend;

// import com.br.ages.orientacaobucalbackend.Entity.Content;
// import com.br.ages.orientacaobucalbackend.Services.ContentService;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;

// import java.util.List;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class ContentTest {

//     @Autowired
//     private ContentService contentService;

//     @Test
//     public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
//         Content content = new Content("text url", "text title", "", "panfleto url");
//         contentService.deleteAll();
//         contentService.save(content);
//         List<Content> contentList= contentService.list();

//         assert(contentList.size() == 1);
//     }
// }