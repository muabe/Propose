#### Propose-2.0 개발이 시작되었습니다.

[![English](https://img.shields.io/badge/-English-blue.svg?style=flat)](https://github.com/muabe/Propose/blob/master/README.md) &nbsp;  [![한국어](https://img.shields.io/badge/-%ED%95%9C%EA%B5%AD%EC%96%B4-red.svg?style=flat)](https://github.com/muabe/Propose/blob/master/README_KR.md)<br><br>

# Android Propose
<i class="icon-cog"></i> 
Android에서 제공하는 Animation은 쉽고 간편함에도 불구하고<br>
우리는 힘들게 CustomView를 만들어 사용합니다.<br>
Animation은 Touch 입력 같은 동적인 조작을 할 수 없기 때문입니다.

**Propose는 놀랍게도 [Property animation](http://developer.android.com/guide/topics/graphics/prop-animation.html)에 Touch 조작을 할 수 있게 해줍니다.<br>
기존의 몇 백 줄의 소스코드를 단 몇 줄로 구현 가능하고<br>
여러 가지 Animation을 합성하여 고차원적인 Interaction을 만들 수 있습니다.<br>
Propose는 당신이 상상하는 모든 것을 가능하게 해줍니다.**
<br><br>

> #### [소개 영상보기](https://youtu.be/v0gIuIK3Ww4) <br>
[![Android Propose Story book](https://github.com/muabe/Minor-League/blob/master/images/propose/book%20flip.png)](https://youtu.be/v0gIuIK3Ww4)<br>
**_Goodbye to CustomView_**

<br><br><br>

### Propose는 Android에서 제공하는 Property Animation을 사용합니다.
___
- 기존에 익숙하게 사용하던 Animation을 이용해 Interaction을 구현할 수 있습니다.
- 복잡한 수식을 사용하지 않고 쉽게 Interaction을 구현할 수 있습니다.
- 모든 Thread를 Property Animation이 처리하여 안전합니다.
<br>

### 당신의 소스코드 수정 없이 Interaction을 추가할 수 있습니다.
___
- Propose는 CustomView처럼 의존성을 가지지 않습니다.
- View를 customize않고 Interaction을 부여 해줍니다.
- 기존에 작성된 CustomView를 포함해 모든 View에 적용가능 합니다.
<br>

# Propose 시작하기
### Gradle 설정
아래와 같이 gradle.build 파일에 디펜던시를 명시합니다.
```
dependencies {
    compile 'com.markjmind.propose:propose:1.1.+'
}
```

### 개발 가이드
[1. Propose란](https://github.com/muabe/Propose/wiki/1.-Propose%EB%9E%80)<br>
[2. 시작하기](https://github.com/muabe/Propose/wiki/2.-%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0)<br>
[3. 모션](https://github.com/muabe/Propose/wiki/3.-%EB%AA%A8%EC%85%98)<br>
[4. 애니메이션 조합](https://github.com/muabe/Propose/wiki/4.-%EC%95%A0%EB%8B%88%EB%A9%94%EC%9D%B4%EC%85%98-%EC%A1%B0%ED%95%A9)<br>
[5. 이벤트](https://github.com/muabe/Propose/wiki/5.-%EC%9D%B4%EB%B2%A4%ED%8A%B8)<br>
<br>

### Propose v2.0 spec
---
- Rotating Wheel 
- Two finger drag, rotation
- Pinch
- Drag and drop
- Rub
- Support simple animation
- Moving layout


<br><br><br>
