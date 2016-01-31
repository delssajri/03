input_file = ARGV.first
# creat function that read file
def print_all(f)
  puts f.read
end
# creat function that find begining into file
def rewind(f)
  f.seek(0)
end
# creat function that print line from file
def print_a_line(line_count, f)
  puts "#{line_count}, #{f.gets.chomp}"
end
# open file
current_file = open(input_file)

puts "First let's print the whole file:\n"
# read file
print_all(current_file)

puts "Now let's rewind, kind of like a tape."
# find begining into file
rewind(current_file)

puts "Let's print three lines:"
# creat variable that count lines into file
current_line += 1
print_a_line(current_line, current_file)

current_line += 1
print_a_line(current_line, current_file)

current_line += 1
print_a_line(current_line, current_file)