# FIKA

![](https://velog.velcdn.com/images/roycewon/post/1bb78993-488a-4ceb-b9e4-a7c2cc7a53f3/image.png)




## 프로젝트 소개

K-Drama에서 등장한 장소를 기반으로 주변 관광지 및 음식점, 카페 등 여행에 대한 가이드가 되어주는 일본인을 위한 서비스 입니다.

> `Googl play store` : [Fika](https://play.google.com/store/apps/details?id=com.fika.fika_project)

## 기술 스택

| Java 11                                                      | Spring Boot                                                  | Spring data jpa                                              | Spring <br>Security                                          | MySql                                                        | Jwt                                                          | Docker                                                       | AWS <br>Ec2, Rds, S3                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png"> | <img src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png"> | ![](https://velog.velcdn.com/images/roycewon/post/2a76f5b3-d8ff-4b5e-b7cd-d03b0dff828f/image.png) | ![](https://velog.velcdn.com/images/roycewon/post/e5012a37-3b55-48a9-969c-1617ed810afe/image.png) | ![](https://velog.velcdn.com/images/roycewon/post/7474fe74-ca76-4e15-8e59-4c08eeb0bf83/image.png) | ![](https://velog.velcdn.com/images/roycewon/post/ec962a40-c117-466a-b211-4cf247d401ff/image.png) | ![](https://velog.velcdn.com/images/roycewon/post/3faf2beb-26c3-43fa-8b36-cf5322823613/image.png) | ![](https://velog.velcdn.com/images/roycewon/post/02214977-9f52-45e9-bf8e-1623c355d6fc/image.png) |

### 백엔드 아키텍쳐

![](https://velog.velcdn.com/images/roycewon/post/427d3bbc-d992-4cf9-af3a-3d5ba9a11915/image.png)




### ERD diagram

![](https://velog.velcdn.com/images/roycewon/post/61011d1f-5b7f-46e5-81f2-fea1d1bf32cf/image.png)


>  Spot_data : [`Tour Api 4.0`](https://api.visitkorea.or.kr/#/)을 활용하여 로케지 근방 관광 장소들을 수집


<br>

## 대표 기능

![](https://velog.velcdn.com/images/roycewon/post/fcc91492-ceff-44d1-9197-eb73fdb379cc/image.png)


![](https://velog.velcdn.com/images/roycewon/post/9ebebae9-0453-43b2-8185-ecafc4404955/image.png)


![](https://velog.velcdn.com/images/roycewon/post/3899cb81-6f0b-42c5-9eeb-e233c249ba35/image.png)


### 세부 기능

> 소셜 로그인 지원

	`Kakao`, `Line`, `Google` 로그인을 지원합니다. 기존에 가입하신 서비스를 통해 간편하고 쉽게 로그인 할 수 있습니다.

> 인기 드라마, 코스 및 장소 추천

	메인 화면에서 인기있는 드라마, 코스, 장소를 추천합니다. 코스와 장소는 많이 스크랩한 순으로 추천됩니다.

> 나만의 코스 설계

	제공되는 장소, 코스를 담을 수 있습니다. 담은 장소들을 기반으로 나만의 코스를 설계할 수 있습니다.
	지도와 함께 제공되는 화면을 통해, 보다 직관적으로 코스를 수정 할 수 있습니다

> 장소에 대한 리뷰 작성

	내 코스의 장소에 대한 리뷰를 남길 수 있습니다. 리뷰에는 평점, 이미지, 내용들을 포함할 수 있습니다.

더 자세한 기능들은 앱을 통해 확인할 수 있어요!