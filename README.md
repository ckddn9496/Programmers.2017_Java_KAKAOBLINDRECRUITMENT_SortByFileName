# Programmers.2017_Java_KAKAOBLINDRECRUITMENT_SortByFileName

## 프로그래머스 > (2017) KAKAO BLIND RECRUITMENT > [3차] 파일명정렬

### 1. 문제설명

문제: https://programmers.co.kr/learn/courses/30/lessons/17686

input으로 파일명 배열 String[] files를 받아온다. 각 파일이름은 `HEAD`, `NUMBER`, `TAIL`부로 구성된다.

* **HEAD**는 숫자가 아닌 문자로 이루어져 있으며, 최소한 한 글자 이상이다.

* **NUMBER**는 한 글자에서 최대 다섯글자 사이의 연속된 숫자로 이루어져 있으며, 앞쪽에 0이 올 수 있다. `0`부터 `99999`사이의 숫자로, `00000`이나 `0101`등도 가능하다

* **TAIL**은 그 나머지 부분으로, 여기에는 숫자가 다시 나타날 수도 있으며, 아무 글자도 없을 수 있다.

문자열을 정렬하는데 규칙이 있다

1)   HEAD 부분을 우선적으로 사전 순 정렬한다. 이때, 대소문자는 구분하지 않는다.

2)   HEAD 부분이 대소문자 차이 외에는 같을 경우, NUMBER의 숫자 순으로 정렬한다.

3)   두 파일의 HEAD부분과, NUMBER의 숫자도 같을 경우, 원래 입력에 주어진 순서를 유지한다.

정렬된 파일명 배열을 return하는 문제.

### 2. 풀이

Filename클래스를 만들어 원래의 filename, HEAD, NUMBER를 저장하게 하였다.

```java
class Filename {
  private String filename;
  private String head;
  private int number;
}
```

처음으로 숫자가 나오는 idx전까지가 HEAD이며 그 이후 최대 5개 까지의 숫자가 NUMBER이다. (최대 5개 때문에 헛걸음을 많이 했었다...)

```java
private void setHeadAndNumber() {
  int numberStartIdx = 0;
  for (int i = 0; i < this.filename.length(); i++) {
    if (Character.isDigit(this.filename.charAt(i))) {
      numberStartIdx = i;
      break;
    }
  }
  this.head = this.filename.substring(0, numberStartIdx);

  String numberStr = "";
  for (int i = numberStartIdx; i < this.filename.length(); i++) {
    if (Character.isDigit(this.filename.charAt(i)) && numberStr.length() < 5) {
      numberStr += this.filename.charAt(i);
    } else {
      break;
    }
  }

  this.number = Integer.parseInt(numberStr);
}
```

정렬 기준을 정해주기 위해 별도의 Comparator<Filename> 변수를 선언하여 구현하였다.

```java
private Comparator<Filename> compByFilename = new Comparator<Filename>() {

@Override
public int compare(Filename f1, Filename f2) {
  return f1.getHead().equalsIgnoreCase(f2.getHead()) ? f1.getNumber() - f2.getNumber() : f1.getHead().compareToIgnoreCase(f2.getHead());
  }
};

```


### 3. 배운점

- Stable Sort vs. Unstable Sort

    위 문제에서 HEAD와 NUMBER가 같을 때 파일명의 순서가 변경되면 안된다. 그렇기에 이 문제에서 정렬은 Stable Sort를 만족해야만 한다.
    
    카카오테크 문제 해설: https://tech.kakao.com/2017/11/14/kakao-blind-recruitment-round-3/
    
    > *정렬 문제가 워낙 많이 쓰이므로 많은 프로그래밍 언어에서 정렬 알고리즘을 기본 함수로 제공하고 있습니다. 자신이 사용하는 프로그래밍 언어에서 안정 정렬 알고리즘을 제공해주는지 알아두시는 게 좋습니다. 코딩 테스트에서 사용된 프로그래밍 언어 중 C++과 Python에는 안정 정렬이 있고, Java와 JavaScript, Swift에는 안정 정렬이 없습니다. PHP 언어는 숫자 값을 고려해 정렬하는 natsort()를 기본 함수로 제공하기도 합니다. (아쉽게도 문제 3과 조건이 달라 그대로는 쓸 수 없지만요.)*

    > *기본 정렬 함수가 안정 정렬을 지원하지 않거나, 이 문제처럼 비교 조건이 까다로운 경우에는 decorate-sort-undecorate 패턴을 이용해서 쉽게 해결할 수도 있답니다.*

    > *이 문제의 정답률은 66.95%였습니다. 언어별로는 C++과 Python 사용자들이 힘들어했습니다. 안정 정렬을 지원해주는 언어인데 도움이 안 되었나 봅니다.*
    
    카카오 테크에서 Java에서는 안정정렬이 없다고 했는데, Comporator를 customize한 sort를 이용하여 문제가 없었던 것 같다.
    
    참고로 Stable Sort는 정렬중에 같은 기준을 가진 원소들끼리는 들어온 순서가 변하지 않는 정렬이며 Unstable Sort는 이가 지켜지지 않는 정렬이다.
    
