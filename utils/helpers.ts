
export const getImageFromName = (name: string) => {
    switch (name) {
      case 'banhmi':
        return require('../assets/images/image_coffee.png');
      case 'phobo':
        return require('../assets/images/image_coffee.png');
      case 'trasua':
        return require('../assets/images/image_coffee.png');
      case 'khobo':
        return require('../assets/images/image_coffee.png');
      default:
        return require('../assets/images/icon.png');
    }
  };
  
  export const getCategoryFromName = (name: string) => {
    switch (name) {
      case 'foods':
        return 'Đồ ăn';
      case 'drinks':
        return 'Đồ uống';
      case 'fruits':
        return 'Hoa quả';
      case 'snacks':
        return 'Đồ ăn nhanh';
      default:
        return 'Khác';
    }
  };
  