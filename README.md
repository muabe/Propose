[![English](https://img.shields.io/badge/-English-blue.svg?style=flat)](https://github.com/muabe/Propose/blob/master/README_EN.md) &nbsp;  [![한국어](https://img.shields.io/badge/-%ED%95%9C%EA%B5%AD%EC%96%B4-red.svg?style=flat)](https://github.com/muabe/Propose/blob/master/README.md)
<br><br>

## Overview - 청혼하기 위해 시작된 프로젝트
![wife](https://github.com/muabe/Propose/blob/master/images/wife.jpg) | 내가 잘하는 것으로 사랑하는 이에게 감동을 전해주고 싶었습니다<br> 이야기책을 만들어 청혼하고 싶었지만 내 마음을 표현하기 위한 시간은 턱없이 부족했죠.<br> 간절함은 Touch Animation Engine을 생각해 냈고<br>이렇게 애정과 정성으로 Propose는 시작되었습니다.
--- | :---



<br>
<br>

## Propose 란
---

모바일앱을 개발하면서 어렵다고 생각한 것중 하나는<br>
앱에서 주의를 끌만한 동적-인터랙션(이하 모션)을 만드는 것 이였습니다.<br>
이미 사람들이 많이 본 것을 비슷하게 따라해 공장에서 찍어낸 것 처럼 보이는 것이 아닌<br>
개발자들은 사람들의 기억에 남는 돋보이는 앱, 게임같이 액티브한 앱을 만들고 싶을 겁니다.<br>
하지만 쉽게 할 수 있는 방법을 찾지 못했습니다.<br><br>

![graph](https://github.com/muabe/Propose/blob/master/images/graph2.gif)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![pager](https://github.com/muabe/Propose/blob/master/images/pager2.gif)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![cube](https://github.com/muabe/Propose/blob/master/images/cube2.gif)
<br><br>
일반적으로 위처럼 모션을 만들기 위해 각 1000 라인으로 구성된 코드가 사용되어야 하는데<br> 
물리적인 좌표 연산과 View를 표현하기 위한 알고리즘들이 필요하기 때문입니다.<br>
더 많은 것을 해야 한다면 게임엔진을 사용해야 할 것입니다.<br>
게임 엔진을 사용한다면 기술을 습득해야하는 어려움과 Android의 API, 리소스를 사용할수 없게 되어 버립니다.<br>
<br>
<br>
### Propose는 쉽게 모션 구현이 가능한 Touch Animatoin 엔진 입니다
---
다시 말해 Propose는 동적-인터랙션을 구현하기 위한 API를 제공합니다.<br>
Propose를 사용한다면 몇 줄의 코드만으로 모션을 만들수 있습니다.<br>
아래 소개영상으로 얼마나 쉽게 모션을 개발할 수 있는지 알 수 있습니다.<br><br>

> #### [소개 영상보기](https://youtu.be/v0gIuIK3Ww4) <br>
[![Android Propose Story book](https://github.com/muabe/Minor-League/blob/master/images/propose/book%20flip.png)](https://youtu.be/v0gIuIK3Ww4)
<br>
<br>
<br>

### 장점 및 활용분야
---
터치 모션의 일반적인 개발 방법으로 canvas를 재정의하고 터치 연산 등 상당한 개발 비용이 소모됩니다.<br>
Propose를 사용한다면 쉬운 모션 구현과 간단한 코드 유지관리가 가능합니다.<br>
아래는 3D Flip 화면 개발시 일반 개발과 Propose를 비교한 도표 입니다.<br>

> 일반 개발 VS Propose

3D Flip 구현 | 일반 개발 | Propose
:---: | :---: | :---:
개발 난이도 | 고급 개발자 | **초급 개발자**
코드 라인수 | 500~100 라인 | **10~30 라인**
재사용/확장성 | 전체 소스코드 수정 | **애니메이션 추가 및 조합**
안정성 | Thread 자체관리(에러율 높음) | **Android SDK에서 Thread 자동관리**

``` 
Propose는 고급스럽고 사용자 친화적 모션(부드럽게) 구현과 모바일 리소스 최소화, 
개발자 휴먼 에러 최소화, 구현 시간 단축 및 개발비용 절감 효과를 기대할 수 있습니다.
특히 기업앱의 고급화, 게임, 이북(동화), 앨범앱 제작 등등 
동적기능 필요한 다양한 분야로 활용될 수 있습니다. 
```

<br>
<br>
<br>

### Propose 개발 환경
---

개발환경 | Android SDK 3.0 이상
--- | --- 
<br>
<br>

### Propose Import
---

Android Gradle 파일에 아래 코드를 추가하여 라이브러리를 Import할 수 있습니다<br>
```
compile 'com.markjmind.propose:propose:1.1.+'
```
> Note : Gradle은 Android의 defalut 빌드툴이며 Propose는 JCenter에서 자동 다운로드 됩니다.

<br>
<br>
<br>

### 개발문서
---
- [Propose 개요](https://github.com/muabe/Propose/wiki/1.-Propose-%EA%B0%9C%EC%9A%94)
- [개발 가이드](https://github.com/muabe/Propose/wiki/2.-%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0)
- [아키텍쳐](https://github.com/muabe/Propose/wiki/architecture)
- [클래스 다이어그램](https://github.com/muabe/Propose/wiki/Class-Diagram)
- [JavaDoc](http://muabe.github.io/Propose/)
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

### LICENSE
---
This copy of Propose is licensed under the<br>
Lesser General Public License (LGPL), version 2.1 ("the License").<br>
See the License for details about distribution rights, and the<br>
specific rights regarding derivate works.<br>
<br>
You may obtain a copy of the License at:<br>
[http://www.gnu.org/licenses/licenses.html](http://www.gnu.org/licenses/licenses.html)
<br>
<br>


 
  
