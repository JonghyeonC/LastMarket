import { useState, useEffect, useRef } from 'react';
// import './ModalBasic.css';
import '../Components/ProfilePageModal.css'

import UploadPage from '../Pages/Register/UploadPage';

function ProfilePageModal({ setModalOpen }) {

    // 모달 끄기 (X버튼 onClick 이벤트 핸들러)
    const closeModal = () => {
        setModalOpen(false);
    };

    // 모달 외부 클릭시 끄기 처리
    // Modal 창을 useRef로 취득
    const modalRef = useRef(null);
    
    useEffect(() => {
        // 이벤트 핸들러 함수
        const handler = (event) => {
            // mousedown 이벤트가 발생한 영역이 모달창이 아닐 때, 모달창 제거 처리
            if (modalRef.current && !modalRef.current.contains(event.target)) {
                setModalOpen(false);
            }
        };
        
        // 이벤트 핸들러 등록
        document.addEventListener('mousedown', handler);
        // document.addEventListener('touchstart', handler); // 모바일 대응
        
        return () => {
            // 이벤트 핸들러 해제
            document.removeEventListener('mousedown', handler);
            // document.removeEventListener('touchstart', handler); // 모바일 대응
        };
    });
    
    const [imageUrls, setImageUrls] = useState([])

    // imageUrls.map((url) => {
    //     formData.append('imgs', url)
    //   })

    return (
        // 모달창을 useRef로 잡아준다.
        <div ref={modalRef} className="modalcontainer">
            <button className="close" onClick={closeModal}>
                X
            </button>
            <div className="profile_img_change_box">
              <UploadPage setImageUrls={setImageUrls} />
            </div>
        </div>
    );
}
export default ProfilePageModal;