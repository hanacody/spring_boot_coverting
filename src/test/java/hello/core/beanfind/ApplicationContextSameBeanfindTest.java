package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * [테스트 시나리오]
 * 1. 같은 타입이 둘 이상 등록되어 있을 때 이름 없이 타입만으로 조회하면 예외(NoUniqueBeanDefinitionException)가 발생한다.
 * 2. 같은 타입이 둘 이상 등록되어 있을 때 이름으로 조회하면 정상적으로 조회된다.
 * 3. 같은 타입이 둘 이상 등록되어 있을 때 해당 타입으로 모두 조회할 수 있다.
 */
class ApplicationContextSameBeanfindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면 중복 오류 발생")
    void findBeanByTypeDuplicate() {
        // assertThrows는 예외가 발생하는지 테스트할 때 사용합니다.
        // (예시: assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));)
        // assertThat은 값이 기대한 대로 일치하는지 테스트할 때 사용합니다.
        // (예시: assertThat(memberRepository).isInstanceOf(MemberRepository.class);)
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면 빈 이름 지정")
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
