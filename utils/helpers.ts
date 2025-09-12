
  
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
  