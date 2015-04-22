package com.cms.web.rest;

import com.cms.Application;
import com.cms.domain.Post;
import com.cms.repository.PostRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PostResource REST controller.
 *
 * @see PostResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PostResourceTest {

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_CONTENT = "SAMPLE_TEXT";
    private static final String UPDATED_CONTENT = "UPDATED_TEXT";

    @Inject
    private PostRepository postRepository;

    private MockMvc restPostMockMvc;

    private Post post;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PostResource postResource = new PostResource();
        ReflectionTestUtils.setField(postResource, "postRepository", postRepository);
        this.restPostMockMvc = MockMvcBuilders.standaloneSetup(postResource).build();
    }

    @Before
    public void initTest() {
        post = new Post();
        post.setTitle(DEFAULT_TITLE);
        post.setContent(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post
        restPostMockMvc.perform(post("/api/posts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(post)))
                .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = posts.get(posts.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPost.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(postRepository.findAll()).hasSize(0);
        // set the field null
        post.setTitle(null);

        // Create the Post, which fails.
        restPostMockMvc.perform(post("/api/posts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(post)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(0);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(postRepository.findAll()).hasSize(0);
        // set the field null
        post.setContent(null);

        // Create the Post, which fails.
        restPostMockMvc.perform(post("/api/posts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(post)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPosts() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the posts
        restPostMockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(post.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
		
		int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post
        post.setTitle(UPDATED_TITLE);
        post.setContent(UPDATED_CONTENT);
        restPostMockMvc.perform(put("/api/posts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(post)))
                .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(databaseSizeBeforeUpdate);
        Post testPost = posts.get(posts.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPost.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void deletePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
		
		int databaseSizeBeforeDelete = postRepository.findAll().size();

        // Get the post
        restPostMockMvc.perform(delete("/api/posts/{id}", post.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
