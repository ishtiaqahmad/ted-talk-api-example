package nl.ted.tech.tedTalkAPI;

import nl.ted.tech.tedTalkAPI.model.TedTalk;
import nl.ted.tech.tedTalkAPI.security.BasicSecurityConfig;
import nl.ted.tech.tedTalkAPI.service.TedTalkDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@ContextConfiguration
@DataJpaTest
@Import({TedTalkDataService.class, BasicSecurityConfig.class})
public class TedTalkInternalSecurityTests {

    @Autowired
    TedTalkDataService dataService;

//    @Configuration
//    @ComponentScan("nl.ted.tech.tedTalkAPI.*")
//    public static class SpringConfig {
//
//    }

    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "userDetailsService")
    public void whenUser_callGetTalk_thenOK() {
        Optional<TedTalk> talk = dataService.getTedTalkById((Long) 1L);
        assertEquals((Long) 1L, talk.get().getId());
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    public void whenAdmin_callGetTalk_thenOK() {
        Optional<TedTalk> talk = dataService.getTedTalkById((Long) 1L);
        assertEquals((Long) 1L, talk.get().getId());
    }

    @Test(expected = AccessDeniedException.class)
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "userDetailsService")
    public void whenUser_callUpdateTalk_thenOK() {
        Optional<TedTalk> talk = dataService.getTedTalkById((Long) 1L);
        talk.get().setAuthor("new Author");
        dataService.updateTedTalk(talk.get(),(Long) 1L);
        assertEquals((Long) 1L, talk.get().getId());
    }

    @Test(expected = AccessDeniedException.class)
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "userDetailsService")
    public void givenUser_callSecured_thenAccessDenied() {
        dataService.deleteTedTalkById(2);
    }

}