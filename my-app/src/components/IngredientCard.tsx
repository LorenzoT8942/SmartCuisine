import React from 'react';
import { FaTrash } from 'react-icons/fa';
import '../CSS/ingredient-card.css';

interface IngredientCardProps {
    name: string;
    quantity: number;
    expirationDate: string | null;
    onDelete: () => void;
}

const IngredientCard: React.FC<IngredientCardProps> = ({ name, quantity, expirationDate }) => {
    return (
        <div className="ingredient-card">
            <div className="ingredient-info">
                <div className="ingredient-details">
                    <h3>{name}</h3>
                    <p>Quantity: {quantity} grams</p>
                    {expirationDate && <p>Expiration Date: {expirationDate}</p>}
                </div>
                <button className="remove-button">
                    <FaTrash />
                </button>
            </div>
        </div>
    );
};

export default IngredientCard;