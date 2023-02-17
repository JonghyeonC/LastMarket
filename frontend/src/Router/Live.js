// import { useRTC } from "../reactMultiRTCHook"
// import { useRef, useEffect } from 'react'

// const Live = function (props) {
//   const { userDocumentId, roomDocumentId } = props // 전역 상태의 값을 가져온다.
//   const [ participants, myVideoRef, myStreamRef ] = useRTC({ userDocumentId, roomDocumentId}, 'socketNamespace');
  
//   return (
//     <div>
//       {/* 내 화면 */}
//       <video
//         ref={myVideoRef}
//         stream={myStreamRef.current}
//         autoPlay
//         muted
//         playsInline
//       />
//       {/* 다른 유저들의 화면 */}
//       {participants.map(({ stream }) => (
//         <OtherVideo
//           key={userDocumentId}
//           stream={stream}
//         />
//       ))}
//     </div>
//   )
// }

// const OtherVideo = function (props) {
//   const ref = useRef(null);

//   useEffect(() => {
//     if (!ref.current) return;
//     ref.current.srcObject = stream;
//   }, [stream]);

//   return (
//       <video
//         ref={ref}
//         autoPlay
//         playsInline
//       />
//   )
// }
// export default Live