import React, { useRef, useState } from 'react';

const CameraCapture = () => {
  const videoRef = useRef(null);
  const canvasRef = useRef(null);
  const [capturedImage, setCapturedImage] = useState(null);

  const startCamera = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      videoRef.current.srcObject = stream;
    } catch (error) {
      console.error('Error accessing camera:', error);
    }
  };

  const captureImage = () => {
    const video = videoRef.current;
    const canvas = canvasRef.current;
    const context = canvas.getContext('2d');

    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;

    context.drawImage(video, 0, 0, canvas.width, canvas.height);

    const imageDataUrl = canvas.toDataURL('image/png');
    setCapturedImage(imageDataUrl);
  };

  const stopCamera = () => {
    const stream = videoRef.current.srcObject;
    const tracks = stream.getTracks();

    tracks.forEach(track => track.stop());
    videoRef.current.srcObject = null;
  };

  const sendImageToServer = async () => {
    const imageFile = dataURLtoFile(capturedImage, 'captured_image.png');

    if (imageFile) {
      const formData = new FormData();
      formData.append('image', imageFile);

      try {
        const response = await fetch('http://localhost:8080/performWebScraping', {
          method: 'POST',
          body: formData,
        });
      
        console.log('Full Response:', response);
      
        if (response.ok) {
         // const result = await response.json();
          console.log('Server Response:');
        } else {
          console.error('Error:', response.status);
        }
      }
       catch (error) {
        console.error('Fetch Error:', error);
      }
    }
    };

  const dataURLtoFile = (dataURL, filename) => {
    const arr = dataURL.split(',');
    const mime = arr[0].match(/:(.*?);/)[1];
    const bstr = atob(arr[1]);
    let n = bstr.length;
    const u8arr = new Uint8Array(n);

    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }

    return new File([u8arr], filename, { type: mime });
  };

  return (
    <div>
      <video ref={videoRef} autoPlay playsInline />
      <div>
        <button onClick={startCamera}>Start Camera</button>
        <button onClick={captureImage}>Capture Image</button>
        <button onClick={stopCamera}>Stop Camera</button>
        <button onClick={sendImageToServer}>Send Image to Server</button>
      </div>
      {capturedImage && (
        <div>
          <h2>Captured Image:</h2>
          <img src={capturedImage} alt="Captured" />
        </div>
      )}
      <canvas ref={canvasRef} style={{ display: 'none' }} />
    </div>
  );
};

export default CameraCapture;