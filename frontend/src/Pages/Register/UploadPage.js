import React, { useRef, useState, useMemo } from "react";
import { useEffect } from "react";
import Cropper from 'react-cropper';
import './UploadPagecss.css'

function UploadPage({setImageUrls}) {
  
  const cropperRef = useRef(null);
  const fileInput = useRef(null);

  const [imageFile, setImageFile] = useState([])

  const [cropperImage, setCroppedImage] = useState([])
  
  const handleButtonClick = e => {
    fileInput.current?.click()
  }

  const handleChange = e => {
    setImageFile((prev) => {
      return e.target.files[0]
    })

    // console.log(imageFile)
    // const imageList = e.target.files
    // let imageUrlList = [...imageFile]
    // console.log(fileInput)
    // console.log(imageUrlList)

    // for (let i = 0 ; i < imageList.length; i ++ ) {
    //   const currentImageUrl = URL.createObjectURL(imageList[i])
    //   imageUrlList.push(currentImageUrl)
    // }
    // if (imageUrlList.length > 10) {
    //   imageUrlList = imageUrlList.slice(0, 10)
    // }

    // setImageFile (
    //   imageUrlList
    // )
  }

  const deleteImage = (id) => {
    setImageFile(imageFile.filter((_, index) => index !== id))
  }

  const showImage = useMemo(() => {
    if (!imageFile && imageFile == null) {
      return <img src="BlankImage.png" alt="비어있는 사진" />
    }
      return imageFile.map((image, id) => {
        return (
        <div>
          <img src={URL.createObjectURL(image)} alt={`${image}-${id}`} width="400px" height="400px" />
          <button onClick={() => deleteImage(id)}>X</button>
        </div>
        )
      })
  }, [imageFile])

  useEffect(() => {
    const serialize = {
      imgs: imageFile
    }
    setImageUrls(serialize)

    }, [imageFile])

  return (
    <React.Fragment>
      <div>
        <div>
          <button onClick={handleButtonClick} className="uploadBtn">사진 업로드</button>
          <input type="file" multiple="multiple" ref={fileInput} onChange={handleChange} style={{ display : "none" }} />
        </div>
        <div className="showImage">
          {showImage}
        </div>
      </div>
    </React.Fragment>
  )
}

export default UploadPage