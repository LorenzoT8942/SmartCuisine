import React from "react";
import { FaTrash } from "react-icons/fa";
import "../../CSS/shopping-list-card.css";

const ShoppingListCard = ({ list, onDelete }) => {
  return (
    <div className="shopping-list-card">
      <span className="shopping-list-name">{list.name}</span>
      <button className="delete-button" onClick={() => onDelete(list.id)}>
        <FaTrash />
      </button>
    </div>
  );
};

export default ShoppingListCard;