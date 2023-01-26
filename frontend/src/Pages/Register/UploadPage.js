import React, { useRef, useState, useMemo } from "react";
import './UploadPagecss.css'

function UploadPage() {

  const fileInput = useRef(null);
  const [imageFile, setImageFile] = useState([])

  const handleButtonClick = e => {
    fileInput.current?.click()
  }

  const handleChange = e => {
    const imageList = e.target.files
    let imageUrlList = [...imageFile]
    console.log(imageUrlList)

    for (let i = 0; i < imageList.length; i ++ ) {
      const currentImageUrl = URL.createObjectURL(imageList[i])
      imageUrlList.push(currentImageUrl)
    }
    if (imageUrlList.length > 10) {
      imageUrlList = imageUrlList.slice(0, 10)
    }

    setImageFile(
      imageUrlList
      )
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
          <img src={image} alt={`${image}-${id}`} width="400px" height="400px" />
          <button onClick={() => deleteImage(id)}>X</button>
        </div>
        )
      })
  }, [imageFile])


  return (
    <React.Fragment>
      <div className="showImage">
        {showImage}
      </div>
      <button onClick={handleButtonClick} className="uploadBtn">사진 업로드</button>
      <input type="file" multiple ref={fileInput} onChange={handleChange} style={{ display : "none" }} />
    </React.Fragment>
  )
}

export default UploadPage