import React, { useState } from 'react';
import axios from 'axios';
import '../../CSS/ingredient-modal.css';


interface Ingredient {
  id: number;
  name: string;
}

interface SelectedIngredient extends Ingredient {
  quantity: number;
  expirationDate: Date;
}

interface AddIngredientModalProps {
  onClose: () => void;
  onSubmit: (quantity: number, expirationDate: string) => void;
}

const AddIngredientModal: React.FC<AddIngredientModalProps> = ({ onClose, onSubmit }) => {
  const [quantity, setQuantity] = useState<number | ''>('');
  const [expirationDate, setExpirationDate] = useState<string>('');
  const [formErrors, setFormErrors] = useState<{ quantity?: string; expirationDate?: string }>({});

  const validateForm = () => {
    const errors: { quantity?: string; expirationDate?: string } = {};

    if (quantity === '' || quantity <= 0) {
      errors.quantity = 'Please enter a valid quantity greater than 0.';
    }

    if (!expirationDate) {
      errors.expirationDate = 'Please select a valid expiration date.';
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSubmit = () => {
    if (validateForm()) {
      onSubmit(quantity as number, expirationDate);
    }
  };

  return (
    <div className="modal-overlay">
        <div className="modal-content">
            <button className="close-button" onClick={onClose}>
                X
            </button>
            <h2>Add Ingredient</h2>
            <form onSubmit={(e) => e.preventDefault()}>
                <div className="form-group">
                    <label>
                        Quantity:
                        <input
                            type="number"
                            value={quantity}
                            onChange={(e) => setQuantity(Number(e.target.value) || '')}
                            placeholder="Enter quantity"
                            className={`input-field ${formErrors.quantity ? 'input-error' : ''}`}
                        />
                    </label>
                    {formErrors.quantity && (
                        <span className="error-message">{formErrors.quantity}</span>
                    )}
                </div>
                <div className="form-group">
                    <label>
                        Expiration Date:
                        <input
                            type="date"
                            value={expirationDate}
                            onChange={(e) => setExpirationDate(e.target.value)}
                            className={`input-field ${formErrors.expirationDate ? 'input-error' : ''}`}
                        />
                    </label>
                    {formErrors.expirationDate && (
                        <span className="error-message">{formErrors.expirationDate}</span>
                    )}
                </div>
                <div className="modal-actions">
                    <button className="confirm-button" type="button" onClick={handleSubmit}>
                        Add
                    </button>
                    <button className="cancel-button" type="button" onClick={onClose}>
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    </div>
);

};

interface IngredientShoppingModalProps {
  onClose: () => void;
  onAdd: () => void;
}

const IngredientShoppingModal: React.FC<IngredientShoppingModalProps> = ({ onClose, onAdd}) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(false);
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [selectedIngredients, setSelectedIngredients] = useState<SelectedIngredient[]>([]);
  const [isAddModalOpen, setAddModalOpen] = useState(false);
  const [currentIngredient, setCurrentIngredient] = useState<Ingredient | null>(null);

  const token = localStorage.getItem('authToken');

  const handleSearch = async () => {
    if (!token) throw new Error('The token is null');
    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;

    setLoading(true);
    try {
      const response = await axios.get(
        `http://localhost:3004/recipes-ingredients/ingredients/search-by-name?name=${searchTerm}`,
        {
          headers: { Authorization: `Bearer ${tokenParsed}` },
        }
      );
      setIngredients(response.data);
    } catch (error) {
      console.error('Error fetching ingredients:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddIngredientClick = (ingredient: Ingredient) => {
    setCurrentIngredient(ingredient);
    setAddModalOpen(true);
  };

  const handleAddIngredient = (quantity: number, expirationDate: string) => {
    if (currentIngredient) {
      setSelectedIngredients((prev) => [
        ...prev,
        {
          ...currentIngredient,
          quantity,
          expirationDate: new Date(expirationDate),
        },
      ]);
      setAddModalOpen(false);
      setCurrentIngredient(null);
    }
  };

  const handleRemoveIngredient = (index: number) => {
    setSelectedIngredients((prev) => prev.filter((_, i) => i !== index));
  };

  const handleConfirm = async () => {
    if (!token) throw new Error('The token is null');
    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;

    try {
    console.log(selectedIngredients);

      await Promise.all(
        selectedIngredients.map((ingredient) =>
          axios.post(
            'http://localhost:3005/api/storage/add-ingredient',
            {
              username: 'fixed_value',
              // Assicurati di includere l'ingredientId nel body
              ingredientId: ingredient.id,
              quantity: ingredient.quantity,
              // Converti la data in stringa (yyyy-mm-dd)
              expirationDate: ingredient.expirationDate.toISOString().split('T')[0],
            },
            {
              headers: { Authorization: `Bearer ${tokenParsed}` },
            }
          )
        )
      );
      onAdd();
      onClose();
    } catch (error) {
      console.error('Error confirming ingredients:', error);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="close-button" onClick={onClose}>
          X
        </button>
        <h2>Search Ingredient</h2>
        <form onSubmit={(e) => e.preventDefault()}>
          <input
            type="text"
            className="search-bar"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
            placeholder="Search for an ingredient"
          />
        </form>
        {loading && <p>Loading...</p>}
        <div className="ingredient-selection">
        <ul className="search-results">
          
        {ingredients.length > 0 ? (
                            ingredients.map((ingredient) => (
                                <li key={ingredient.id} onClick={() => handleAddIngredientClick(ingredient)}>
                                    {ingredient.name}
                                </li>
                            ))
                        ) : (
                            <p>No ingredients found</p>
                        )}
          
        </ul>
          <div className="vertical-line"></div>
          <div className="selected-ingredients">
          <h3>Selected Ingredients</h3>
            <ul>
            {selectedIngredients.map((selected, index) => (
            <li key={selected.id}>
              {selected.name}: 
              <br />
              {selected.quantity} grams
              <br />
              {selected.expirationDate.toISOString().split('T')[0]}
              <br />
              <button className="remove-button" onClick={() => handleRemoveIngredient(index)}>X</button>
            </li>
            ))}
          </ul>
        </div>
        </div>
        <button className="confirm-button" onClick={handleConfirm}>Confirm</button>
        <button className="cancel-button" onClick={onClose}>Cancel</button>

        {isAddModalOpen && (
          <AddIngredientModal onClose={() => setAddModalOpen(false)} onSubmit={handleAddIngredient} />
        )}
      </div>
    </div>
  );
};

export default IngredientShoppingModal;
