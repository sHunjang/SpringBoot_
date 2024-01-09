// HTML에서 id를 delete-btn 으로 설정한 엘리먼트를 찾음
// 해당 엘리먼트에서 클릭 이벤트가 발생
// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {  // fetch() 메서드 : /api/articles/ DELETE 요청을 보냄
            method: 'DELETE'
        })
            .then(() => {  // fetch().then() : fetch()가 잘 완료되면 연이어 실행됨
                alert('삭제가 완료되었습니다.');  // alert() 메서드 : 웹 브라우저 화면으로 삭제가 완료되었음을 알리는 팝업을 띄워주는 메서드
                location.replace('/articles');
                // location.replace() : 실행 시 사용자의 웹 브라우저 화면을 현재 주소를 기반해 옮겨줌.
            });
    });
}

// 수정 기능
// 1. id 가 modify-btn 인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
// 2. 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/articles/${id}`);
            });
    });
}

// 생성 기능
// 1. id 가 create-btn 인 엘리먼트
const createButton = document.getElementById('create-btn');

if (createButton) {
// 2. 클릭 이벤트가 감지되면 생성 API 요청
    createButton.addEventListener('click', event => {
        fetch('/api/articles', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('등록 완료되었습니다.');
                location.replace('/articles');
            });
    });
}
