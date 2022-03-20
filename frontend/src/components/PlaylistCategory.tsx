import classNames from "classnames";
import { Link } from "react-router-dom";
import { PlaylistCategoryProps } from "../shared/Props";

const PlaylistCategory = ({ page }: PlaylistCategoryProps) => {
  return (
    <div className="category__container">
      <Link
        to="/"
        className={classNames("category__link", {
          isBright: page === "/"
        })}
      >
        내 플레이리스트
      </Link>
      <Link
        to="/"
        state="allPlaylist"
        className={classNames("category__link", {
          isBright: page === "/allPlaylist",
        })}
      >
        모든 플레이리스트
      </Link>
      <Link
        to="/"
        state="bookmarks"
        className={classNames("category__link", {
          isBright: page === "/bookmarks",
        })}
      >
        즐겨찾기
      </Link>
    </div>
  );
};

export default PlaylistCategory;
