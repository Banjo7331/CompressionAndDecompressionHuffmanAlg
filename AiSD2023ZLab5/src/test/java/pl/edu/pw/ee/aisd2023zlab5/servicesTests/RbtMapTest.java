package pl.edu.pw.ee.aisd2023zlab5.servicesTests;

import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.services.map.RbtMap;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class RbtMapTest {
    protected RbtMap<String,String> redBlackTree;
    @Before
    public void setup() {
        redBlackTree = new RbtMap<>();
    }
    @Test
    public void Adding_weronika_Test() {
        // given
        RbtMap<Character,Character> rbtMap = new RbtMap();
        String a= "weronika";
        char[] charArray = a.toCharArray();

        for(int i = 0; i < a.length(); i++){
            rbtMap.setValue(charArray[i],charArray[i]);
        }
        assertThat(rbtMap.getSize() == a.length());
        //rbtMap.showTree();
    }
    @Test
    public void Adding_biedronka_Test() {
        RbtMap<Character,Character> rbtMap = new RbtMap();
        String a= "biedronka";
        char[] charArray = a.toCharArray();

        for(int i = 0; i < a.length(); i++){
            rbtMap.setValue(charArray[i],charArray[i]);
        }
        assertThat(rbtMap.getSize() == a.length());
        //rbtMap.showTree();


    }
    @Test
    public void should_Rewrite_Value_When_Adding_One_WithSameKey(){
        RbtMap<Integer,String> rbtMap = new RbtMap();
        String start= "start";
        Integer key = 1234;

        rbtMap.setValue(key,start);

        rbtMap.showTree();

        String end= "end";

        rbtMap.setValue(key,end);
        assertThat(rbtMap.getSize()==1);
        //rbtMap.showTree();

    }
    @Test
    public void Adding_And_Getting_100kIntegers() {
        RbtMap<Integer,Integer> rbtMap = new RbtMap();
        int size = 100000;
        for(int i = 0; i < size; i++){
            rbtMap.setValue(i,i);
        }
        for(Integer i = 0; i < size; i++){
            rbtMap.getValue(i);
            assertThat(i).isEqualTo(rbtMap.getValue(i));
        }
    }
    @Test
    public void should_ThrowException_WhenTryingAddNullValue() {
        RbtMap<Integer,String> rbtMap = new RbtMap();
        String nullValue = null;

        Throwable exceptionCaught = catchThrowable(() -> {
            rbtMap.setValue(0,nullValue);
        });

        String message = "Params (key, value) cannot be null.";

        assertThat(exceptionCaught)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }
    @Test
    public void should_ThrowException_WhenTryingAddNullValue_AndNullKey() {
        RbtMap<Integer,String> rbtMap = new RbtMap();
        String nullValue = null;

        Throwable exceptionCaught = catchThrowable(() -> {
            rbtMap.setValue(null,nullValue);
        });

        String message = "Params (key, value) cannot be null.";

        assertThat(exceptionCaught)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }
    @Test
    public void should_ThrowException_WhenTryingAddNullKey() {
        RbtMap<Integer,String> rbtMap = new RbtMap();
        String nullValue = null;

        Throwable exceptionCaught = catchThrowable(() -> {
            rbtMap.setValue(null,nullValue);
        });

        String message = "Params (key, value) cannot be null.";

        assertThat(exceptionCaught)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @Test
    public void should_ThrowException_WhenTryingGetNullValue() {
        RbtMap<Integer,String> rbtMap = new RbtMap();
        Integer nullKey = null;

        Throwable exceptionCaught = catchThrowable(() -> {
            rbtMap.getValue(nullKey);
        });

        String message = "Cannot get value by null key.";
        assertThat(exceptionCaught)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }
    @Test
    public void should_Add_100k_Random_Words_WithRandomLengths() {
        RbtMap<String ,String> rbtMap = redBlackTree;

        int size = 100_000;
        for(int i = 0; i < size; i++){
            String randW = generateRandomWord(false,Integer.MIN_VALUE);
            rbtMap.setValue(randW,randW);
        }
        assertThat(rbtMap.getSize() == size);
    }
    @Test
    public void should_Add_100k_Random_Words_WithTheSameLength() {
        RbtMap<String ,String> rbtMap = redBlackTree;

        int size = 100_000;
        int sizeOfWord = 7;
        for(int i = 0; i < size; i++){
            String randW = generateRandomWord(true, sizeOfWord);
            rbtMap.setValue(randW,randW);
        }
        assertThat(rbtMap.getSize() == size);
    }


    public static String generateRandomWord(boolean sameLength, int size) {
        Random random = new Random();
        StringBuilder randomWord = new StringBuilder();

        char minAsciAlf = 97;
        char maxAsciAlf  = 122;

        if(!sameLength) {
            for (int i = 0; i < random.nextInt(10); i++) {
                int randomIndex = random.nextInt(maxAsciAlf - minAsciAlf + 1) + minAsciAlf;
                char randomChar = (char) randomIndex;
                randomWord.append(randomChar);
            }
        }else{
            for (int i = 0; i < size; i++) {
                int randomIndex = random.nextInt(maxAsciAlf - minAsciAlf + 1) + minAsciAlf;
                char randomChar = (char) randomIndex;
                randomWord.append(randomChar);
            }
        }

        return randomWord.toString();
    }
    @Test
    public void Adding_weronika_AndTestingDeleteMethod() {
        RbtMap<Character,Character> rbtMap = new RbtMap();
        String a= "weronika";
        char[] charArray = a.toCharArray();

        for(int i = 0; i < a.length(); i++){
            rbtMap.setValue(charArray[i],charArray[i]);
        }
        assertThat(rbtMap.getSize() == a.length());
        rbtMap.deleteMaxValue();
        assertThat(rbtMap.getSize() == a.length()-1);
    }
    @Test
    public void should_Add_100k_Random_Words_WithTheSameLength_AndDeleteThem() {
        RbtMap<String ,String> rbtMap = redBlackTree;

        int size = 100_000;
        int sizeOfWord = 7;
        for(int i = 0; i < size; i++){
            String randW = generateRandomWord(true, sizeOfWord);
            rbtMap.setValue(randW,randW);
        }
        assertThat(rbtMap.getSize() == size);
        for(int i = 0; i < size; i++){
            rbtMap.deleteMaxValue();
        }
        assertThat(rbtMap.getSize() == 0);

    }

}
