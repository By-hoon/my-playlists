import { SongProps } from "../shared/Props";
import { useCallback, useState } from "react";

const YoutubeVideo = ({ song }: SongProps) => {
  const [description, setDescription] = useState(song.description);
  const onChange = useCallback((e) => {
    setDescription(e.target.value);
  }, []);
  const onClickEdit = () => {
    console.log(description);
    // 새로운 description 저장 로직 추가
  };
  const onClickDelete = () => {
    // 노래 삭제 로직 추가
  };
  return (
    <div className="youtube__container">
      <video
        tabIndex={-1}
        className="youtube__video"
        controlsList="nodownload"
        src={song.videoId}
      ></video>
      <div className="description__container--youtube">
        <textarea
          className="description__textarea"
          value={description}
          onChange={onChange}
        />
      </div>
      <div className="button__container--youtube">
        <span className="button__span--edit" onClick={onClickEdit}>
          수정
        </span>
        <span className="button__span--delete" onClick={onClickDelete}>
          삭제
        </span>
      </div>
      <div className="thumbnail__container--youtube">
        <img
          className="thumbnail__img--youtube"
          src={song.thumbnail}
          alt="youtube_thumbnail"
        />
      </div>
    </div>
  );
};

export default YoutubeVideo;
