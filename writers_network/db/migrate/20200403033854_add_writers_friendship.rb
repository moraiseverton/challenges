class AddWritersFriendship < ActiveRecord::Migration[5.1]
  def change
    create_table :friendships, :force => true, :id => false do |t|
      t.integer :writer_a_id, :null => false
      t.integer :writer_b_id, :null => false
    end
  end
end
