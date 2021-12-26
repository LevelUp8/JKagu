#!/bin/bash
# A simple script for creating shortcut in Ubuntu
current_dir=$(pwd)
search=change_path

echo $current_dir
echo $search

if [[ $search != "" && $current_dir != "" ]]; then
sed -i -e "s#$search#$current_dir#g" JKagu.desktop
fi

cp ./JKagu.desktop ~/.local/share/applications