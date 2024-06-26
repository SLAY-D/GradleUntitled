package utils;

import com.github.javafaker.Faker;
import models.Swagger.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RandomTestData {
    private static Random random;
    private static Faker faker = new Faker();

    public static GamesItem getRandomGame() {
        SimilarDlc similarDlc = SimilarDlc.builder()
                .isFree(false)
                .dlcNameFromAnotherGame(faker.funnyName().name())
                .build();

        DlcsItem dlcsItem = DlcsItem.builder()
                .rating(faker.random().nextInt(10))
                .price(faker.random().nextInt(1, 500))
                .description(faker.funnyName().name())
                .dlcName(faker.dragonBall().character())
                .isDlcFree(false)
                .similarDlc(similarDlc).build();


        Requirements requirements = Requirements.builder()
                .ramGb(faker.random().nextInt(4, 16))
                .osName("Windows")
                .hardDrive(faker.random().nextInt(30, 70))
                .videoCard("NVIDIA")
                .build();


        return GamesItem.builder()
                .requirements(requirements)
                .genre(faker.book().genre())
                .price(random.nextInt(400))
                .description(faker.funnyName().name())
                .company(faker.company().name())
                .isFree(false)
                .title(faker.beer().name())
                .rating(faker.random().nextInt(10))
                .publishDate(LocalDateTime.now().toString())
                .requiredAge(random.nextBoolean())
                .tags(Arrays.asList("shooter", "quests"))
                .dlcs(Collections.singletonList(dlcsItem))
                .build();
    }

    public static FullUser getRandomUserWithGames(){
        random = new Random();
        int randomNumber = Math.abs(random.nextInt()); // Необходимо для того, чтобы всегда было уникальное имя пользователя
        GamesItem gamesItem = getRandomGame();

        return FullUser.builder()
                .login("CombuchaUser" + randomNumber)
                .pass("gribochekPass")
                .games(Collections.singletonList(gamesItem))
                .build();
    }

    public static FullUser getRandomUser(){
        random = new Random();
        int randomNumber = Math.abs(random.nextInt()); // Необходимо для того, чтобы всегда было уникальное имя пользователя

        return FullUser.builder()
                .login("CombuchaUser" + randomNumber)
                .pass("gribochekPass")
                .build();
    }

    public static FullUser getAdminUser(){
        return FullUser.builder()
                .login("admin")
                .pass("admin")
                .build();
    }
}
