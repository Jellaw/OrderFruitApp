import { Ionicons, MaterialIcons } from '@expo/vector-icons';
import React from 'react';
import { StyleSheet, TouchableOpacity, View } from 'react-native';

interface TabSwitchProps {
  activeIndex: number;
  onChange: (index: number) => void;
}

const TabSwitch: React.FC<TabSwitchProps> = ({ activeIndex, onChange }) => {
  return (
    <View style={styles.container}>
      {/* Location */}
      <TouchableOpacity
        style={[styles.tab, activeIndex === 0 && styles.activeTab]}
        onPress={() => onChange(0)}
      >
        <Ionicons
          name="location-outline"
          size={20}
          color={activeIndex === 0 ? '#fff' : '#7d7d7d'}
        />
      </TouchableOpacity>

      {/* Card */}
      <TouchableOpacity
        style={[styles.tab, activeIndex === 1 && styles.activeTab]}
        onPress={() => onChange(1)}
      >
        <Ionicons
          name="card-outline"
          size={20}
          color={activeIndex === 1 ? '#fff' : '#7d7d7d'}
        />
      </TouchableOpacity>

      {/* List */}
      <TouchableOpacity
        style={[styles.tab, activeIndex === 2 && styles.activeTab]}
        onPress={() => onChange(2)}
      >
        <MaterialIcons
          name="list"
          size={20}
          color={activeIndex === 2 ? '#fff' : '#7d7d7d'}
        />
      </TouchableOpacity>
    </View>
  );
};

export default TabSwitch;

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginVertical: 15,
    marginBottom:50,
  },
  tab: {
    width: 90,
    height: 60,
    borderRadius: 30,
    backgroundColor: 'transparent',
    justifyContent: 'center',
    alignItems: 'center',
    marginHorizontal: 5,
  },
  activeTab: {
    backgroundColor: '#bdb5aa', // màu xám nhạt giống ảnh
  },
});
