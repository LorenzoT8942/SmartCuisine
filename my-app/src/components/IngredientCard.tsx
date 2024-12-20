const IngredientCard = ({ name, quantity }) => {
    const cardStyles = {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '10px 20px',
        border: '1px solid #ccc',
        borderRadius: '8px',
        backgroundColor: '#f9f9f9',
        marginBottom: '10px', // Spacing for multiple cards
        width: '20%',
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    };

    const nameStyles = {
        fontWeight: 'bold',
        fontSize: '16px',
        color: '#333',
    };

    const quantityStyles = {
        fontSize: '16px',
        color: '#666',
    };

    return (
        <div style={cardStyles}>
            <span style={nameStyles}>{name}</span>
            <span style={quantityStyles}>{quantity}</span>
        </div>
    );
};

export default IngredientCard;