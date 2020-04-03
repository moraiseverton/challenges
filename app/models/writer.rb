class Writer < ApplicationRecord
    validates :name, :website, presence: true

    has_and_belongs_to_many(:writer,
      :join_table => :friendships,
      :foreign_key => :writer_a_id,
      :association_foreign_key => :writer_b_id)

    def self.possible_friends
      puts "name::  ", :id
      Writer.where("id != ?", :id)
    end
end
