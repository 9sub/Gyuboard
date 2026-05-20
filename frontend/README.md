# Vue Board Frontend

JSP 화면을 제거하고 Spring REST API와 붙일 수 있도록 만든 Vue 3 + Vite SPA 버전입니다.

## 실행

```bash
npm install
npm run dev
```

기본 개발 서버는 `http://localhost:5173`입니다.
Vite proxy가 `/api` 요청을 `http://localhost:8080`으로 전달합니다.

## Spring 쪽에서 필요한 REST API

```text
GET    /api/me
POST   /api/member/login
POST   /api/member/join
POST   /api/member/logout

GET    /api/boards?page=1&type=all&keyword=&sort=latest
POST   /api/boards
GET    /api/boards/{id}
PUT    /api/boards/{id}
DELETE /api/boards/{id}

POST   /api/boards/{id}/comments
DELETE /api/comments/{commentId}
```

## 권장 응답 형태

### 현재 로그인 사용자

```json
{
  "userId": "hong",
  "writer": "홍길동",
  "name": "홍길동"
}
```

로그인하지 않은 경우 `/api/me`는 `401 Unauthorized`를 반환하면 됩니다.

### 게시글 목록

```json
{
  "list": [
    {
      "id": 1,
      "userId": "hong",
      "writer": "홍길동",
      "title": "제목",
      "guecontents": "내용",
      "writedate": "2026-05-20T10:30:00",
      "viewCount": 3
    }
  ],
  "pageDto": {
    "page": 1,
    "startPage": 1,
    "endPage": 5,
    "prev": false,
    "next": true
  }
}
```

### 게시글 상세

```json
{
  "board": {
    "id": 1,
    "userId": "hong",
    "writer": "홍길동",
    "title": "제목",
    "guecontents": "내용",
    "writedate": "2026-05-20T10:30:00",
    "viewCount": 3
  },
  "comments": [
    {
      "id": 10,
      "boardId": 1,
      "userId": "hong",
      "writer": "홍길동",
      "content": "댓글",
      "createdAt": "2026-05-20T11:00:00"
    }
  ]
}
```

## CORS / 세션 사용 시 주의

Vue dev 서버와 Spring 서버 포트가 다르면 Spring에서 CORS 설정이 필요합니다.
세션 로그인 방식을 유지하려면 프론트는 `withCredentials: true`로 요청하고 있으므로, Spring CORS에서도 `allowCredentials(true)`가 필요합니다.

## 파일 구조

```text
src/
├── api/
│   ├── auth.js
│   ├── boards.js
│   └── http.js
├── components/
│   ├── ErrorMessage.vue
│   └── Pagination.vue
├── router/index.js
├── stores/authStore.js
├── views/
│   ├── HomeView.vue
│   ├── LoginView.vue
│   ├── JoinView.vue
│   ├── BoardListView.vue
│   ├── BoardDetailView.vue
│   ├── BoardWriteView.vue
│   └── BoardUpdateView.vue
├── App.vue
├── main.js
└── style.css
```
