import React, { useRef, useState, useMemo } from "react";

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

  const showImage = useMemo(() => {
    if (!imageFile && imageFile == null) {
      return <img src="BlankImage.png" alt="비어있는 사진" />
    }
      return imageFile.map((image, id) => {
        return <img src={image} alt={`${image}-${id}`} />
      })
  }, [imageFile])

  const deleteImage = (id) => {}
  return (
    <React.Fragment>
      <div>
        {showImage}
      </div>
      <button onClick={handleButtonClick}>사진 업로드</button>
      <input type="file" multiple ref={fileInput} onChange={handleChange} style={{ display : "none" }} />
    </React.Fragment>
  )
}

export default UploadPage