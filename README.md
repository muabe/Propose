## Overview - 청혼하기 위해 시작된 프로젝트
![wife](https://github.com/muabe/Propose/blob/master/images/wife.jpg) <br>
 내가 잘하는 것으로 사랑하는 이에게 감동을 전해주고 싶었습니다<br>
 이야기책을 만들어 청혼하고 싶었지만 내 마음을 표현하기 위한 시간은 턱없이 부족했죠.<br>
 간절함은 Animation과 Motion의 조합을 생각해 냈고 이렇게 애정과 정성으로 Propose는 시작되었습니다.
<br>
<br>

# Propose 란
---

모바일앱을 개발하면서 어렵다고 생각한 것중 하나는<br>
앱에서 주의를 끌만한 동적-인터렉션(이하 모션)을 만드는 것이였습니다.<br>
이미 사람들이 많이 본 것을 비슷하게 따라해 공장에서 찍어낸것처럼 보이는 것이 아닌<br>
개발자들은 사람들의 기억에 남는 돋보이는 앱, 게임같이 액티브한 앱을 만들고 싶을겁니다.<br>
하지만 쉽게 할 수 있는 방법을 찾지 못했습니다.<br><br>

![3d_flip](https://github.com/muabe/Propose/blob/master/images/pageFlipping.gif)
<br><br>
위 처럼 책장을 넘기는 모션을 만들기 위해 1000 라인으로 구성된 코드가 사용되었는데<br> 
물리적인 좌표 연산과 View를 표현하기 위한 알고리즘들이 필요 했습니다.<br>

더 많은것을 해야한다면 게임엔진을 사용해야 할것입니다.<br>
엔진을 사용한다면 기술을 습득해야하는 어려움과 Android의 API, 리소스를 사용할수 없게 되어 버립니다.<br>

### Propose는 모션을 쉽게 구현할수 있는 안드로이드 오픈 라이브러리 입니다
다시말해 Propose는 동적-인터렉션을 구현하기 위한 API를 제공합니다.<br>
<br>
Propose를 사용한다면 몇줄의 코드만으로 책장을 넘기는 모션을 만들수 있습니다.<br>
아래 소개영상으로 얼마나 쉽게 모션을 개발할 수 있는지 알수 있습니다.<br><br>

> #### [소개 영상보기](https://youtu.be/v0gIuIK3Ww4) <br>
[![Android Propose Story book](https://github.com/muabe/Minor-League/blob/master/images/propose/book%20flip.png)](https://youtu.be/v0gIuIK3Ww4)
<br>
<br>
<br>
<br>


## Propose Import
---
Android Gradle 파일에 아래 코드를 추가하여 라이브러리를 Import 할수 있습니다<br>
```
compile 'com.markjmind.propose:propose:1.1.+'
```
(Note : Gradle은 Android의 defalut 빌드툴이며 Propose는 JCenter에서 자동 다운로드 됩니다.)
<br>
<br>
<br>

### 개발문서
---
모든 개발문서는 [Github Wiki](https://github.com/muabe/Propose/wiki/Android-Propose)에 작성 되었습니다.
- [Propose 개요](https://github.com/muabe/Propose/wiki/1.-Propose-%EA%B0%9C%EC%9A%94)
- [개발 가이드](https://github.com/muabe/Propose/wiki/2.-%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0)
- [아키텍쳐](https://github.com/muabe/Propose/wiki/architecture)
- [클래스 다이어그램](https://github.com/muabe/Propose/wiki/Class-Diagram)
- [JavaDoc](https://github.com/muabe/Propose/wiki/Samples)
- [Sample](https://github.com/muabe/Propose/wiki/Samples)
<br>
<br>

### 프로젝트 관리
---
 - [Trello](https://trello.com/b/pYiqclvp/propose)
 - [Issues](https://github.com/muabe/Propose/issues)
 - [ _QnA_ ](https://github.com/muabe/Propose/issues/new)
 
<br>
<br>





 
  
