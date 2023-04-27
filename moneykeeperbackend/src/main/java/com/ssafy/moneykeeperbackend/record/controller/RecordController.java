package com.ssafy.moneykeeperbackend.record.controller;

import com.ssafy.moneykeeperbackend.budget.entity.Account;
import com.ssafy.moneykeeperbackend.budget.entity.Budget;
import com.ssafy.moneykeeperbackend.budget.repository.AccountRepository;
import com.ssafy.moneykeeperbackend.budget.repository.BudgetRepository;
import com.ssafy.moneykeeperbackend.budget.service.BudgetService;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.record.dto.SpendingRequestDto;
import com.ssafy.moneykeeperbackend.record.dto.SpendingResponseDto;
import com.ssafy.moneykeeperbackend.record.entity.Asset;
import com.ssafy.moneykeeperbackend.record.entity.Spending;
import com.ssafy.moneykeeperbackend.record.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.record.repository.AssetRepository;
import com.ssafy.moneykeeperbackend.record.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.record.service.SpendingRecordService;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {
    private final SpendingClassificationRepository spendingClassificationRepository;
    private final MemberRepository memberRepository;

    private final AssetRepository assetRepository;
    private final BudgetRepository budgetRepository;
    private final AccountRepository accountRepository;
    private final SpendingRecordService spendingRecordService;
    private final BudgetService budgetService;
    @PostMapping("/spending")
    @Operation(summary = "지출 기록", description = "지출을 기록한다"
            , responses = {
    })
    public ResponseEntity<?> recordSpending(@RequestParam("id") String id, @RequestPart("spending") SpendingRequestDto spending) { // 나중에 바꿀 것
        spendingRecordService.recordSpending(spending, Long.parseLong(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/spending/{year}/{month}")
    @Operation(summary = "특정 달 지출 조회", description = "지출을 기록한다"
            , responses = {
    })
    public ResponseEntity<?> viewXMonthSpending(@RequestParam("id") String id, @PathVariable int year, @PathVariable int month) { // 나중에 바꿀 것
        List<SpendingResponseDto> spendings = spendingRecordService.viewXMonthSpending(Long.parseLong(id),year,month);

        return new ResponseEntity<List<SpendingResponseDto>>(spendings,HttpStatus.OK);
    }

    @GetMapping("/spending/{id}")
    @Operation(summary = "특정 지출 조회", description = "지출을 기록한다"
            , responses = {
    })
    public ResponseEntity<?> viewSpending(@RequestParam("id") String id, @PathVariable int year, @PathVariable int month) { // 나중에 바꿀 것
        List<SpendingResponseDto> spendings = spendingRecordService.viewXMonthSpending(Long.parseLong(id),year,month);

        return new ResponseEntity<List<SpendingResponseDto>>(spendings,HttpStatus.OK);
    }


    // for test
    @PostMapping("/spending/{category}")
    @Operation(summary = "카테고리 추가", description = "지출을 기록한다"
            , responses = {
    })
    public ResponseEntity<?> addCatForTest(@PathVariable String category) {
        // take name, comment
        spendingRecordService.addCatForTest(category);
        return ResponseEntity.ok().build();
    }








    @GetMapping("/initfortest")
    @Operation(summary = "호출시 영화,배달,외식 총 3개의 카테고리가 들어감, 테스트용 멤버도 생성됨(이후 id 1쓰면 됨)", description = "카테고리 및 멤버 생성"
            , responses = {
    })
    public ResponseEntity<?> initForTest() { // 나중에 바꿀 것
        // take name, comment
        SpendingClassification spendingClassification1 = SpendingClassification.builder()
                .name("영화").build();
        SpendingClassification spendingClassification2 = SpendingClassification.builder()
                .name("배달").build();
        SpendingClassification spendingClassification3 = SpendingClassification.builder()
                .name("외식").build();
        Member member = Member.builder()
                .email("email")
                .oauth("oauth")
                .nickname("nickname")
                .password("hello")
                .oauthAceessToken("oauthAccessToken")
                .build();

        spendingClassificationRepository.save(spendingClassification1);
        spendingClassificationRepository.save(spendingClassification2);
        spendingClassificationRepository.save(spendingClassification3);
        memberRepository.save(member);
        Member member1 = memberRepository.findByNicknameAndEmail("nickname","email");

        budgetService.setAccount(member1.getId(),4000000);
        budgetService.setBudget(member1.getId(),10000000);

        Asset asset1 = Asset.builder().name("하나은행").build();
        Asset asset2 = Asset.builder().name("신한은행").build();
        Asset asset3 = Asset.builder().name("카카오뱅크").build();

        assetRepository.save(asset1);
        assetRepository.save(asset2);
        assetRepository.save(asset3);
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(now.getYear(),now.getMonth(),1);
        return new ResponseEntity<Long>(member1.getId(), HttpStatus.OK);
    }

    @GetMapping("/initForTest2")
    @Operation(summary = "지금은 무시하면 됨 테스트 편하게 하려고 만든 엔드포인트", description = "카테고리 및 멤버 생성"
            , responses = {
    })
    public ResponseEntity<?> initForTest2() { // 나중에 바꿀 것
        return ResponseEntity.ok().build();
    }
}
